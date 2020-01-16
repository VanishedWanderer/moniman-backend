package at.vanishedwanderer.person.control

import at.vanishedwanderer.AdvancedPanacheRepository
import io.quarkus.hibernate.orm.panache.PanacheRepository
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
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

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
}

@ApplicationScoped
class PersonDao() : AdvancedPanacheRepository<Person>, PanacheRepository<Person> {

}

@Entity
open class Person(
        @Id
        @GeneratedValue
        public var id: Long? = null) {

    open lateinit var firstName: String
    open lateinit var lastName: String

}