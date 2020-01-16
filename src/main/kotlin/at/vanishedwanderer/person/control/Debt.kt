package at.vanishedwanderer.person.control

import at.vanishedwanderer.AdvancedPanacheRepository
import io.quarkus.hibernate.orm.panache.PanacheRepository
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.persistence.*
import javax.transaction.Transactional
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response


@Path("/debts")
@Produces(MediaType.APPLICATION_JSON)
class DebtEndpoint() {

    @Inject
    protected lateinit var debtDao: DebtDao

    @GET
    fun getAll(): Response {
        val list = debtDao.findAll().list<Debt>()
        return Response.ok(list).build();
    }

    @POST
    @Transactional
    fun save(debt: Debt): Response {
        debtDao.persist(debt)
        debtDao.flush()
        debtDao.refresh(debt)
        return Response.ok(debt).build()
    }
}

@ApplicationScoped
class DebtDao() : AdvancedPanacheRepository<Debt>, PanacheRepository<Debt> {

}

@Entity
open class Debt(
        @Id
        @GeneratedValue
        public var id: Long? = null) {

    open var amount: Double = 0.0

    open var confirmed: Boolean = false

    @ManyToOne(targetEntity = Person::class)
    open lateinit var debtor: Person

    @ManyToOne(targetEntity = Person::class)
    open lateinit var creditor: Person

}