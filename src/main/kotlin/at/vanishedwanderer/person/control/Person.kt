package at.vanishedwanderer.person.control

import at.vanishedwanderer.AdvancedPanacheRepository
import io.quarkus.hibernate.orm.panache.Panache
import io.quarkus.hibernate.orm.panache.PanacheRepository
import io.quarkus.security.Authenticated
import io.smallrye.jwt.auth.principal.JWTCallerPrincipal
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.transaction.Transactional
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import javax.ws.rs.core.SecurityContext

@Path("/persons")
@Produces(MediaType.APPLICATION_JSON)
class PersonEndpoint() {

    @Inject
    protected lateinit var personDao: PersonDao

    @GET
    fun getAll(): Response {
        val list = personDao.findAll().list<Person>()
        return Response.ok(list).build();
    }

    @POST
    @Transactional
    fun create(person: Person): Response {
        personDao.persist(person)
        personDao.flush()
        personDao.refresh(person)
        return Response.ok(person).build()
    }

    @Path("me")
    @Authenticated
    @GET
    fun getMe(@Context sec: SecurityContext): Response {
        val principal = sec.userPrincipal as JWTCallerPrincipal
        val person = personDao.forUser(principal)
        return Response.ok(person).build()
    }
}

@ApplicationScoped
class PersonDao() : AdvancedPanacheRepository<Person>, PanacheRepository<Person> {

    @Transactional
    fun forUser(principal: JWTCallerPrincipal): Person {

        val user = find("subject", principal.subject).singleResultOptional<Person>()
        return if(user.isEmpty){
            val newPerson = Person().apply {
                firstName = principal.claim<String>("given_name").get()
                lastName = principal.claim<String>("family_name").get()
                subject = principal.subject
            }
            persist(newPerson)
            flush()
            refresh(newPerson)
            newPerson
        }else{
            user.get()
        }
    }
}

@Entity
open class Person(
        @Id
        @GeneratedValue
        public var id: Long? = null) {

    open lateinit var firstName: String
    open var subject: String? = null
    open lateinit var lastName: String

}