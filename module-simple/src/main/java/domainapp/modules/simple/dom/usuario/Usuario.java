package domainapp.modules.simple.dom.usuario;

import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.DatastoreIdentity;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.Queries;
import javax.jdo.annotations.Query;
import javax.jdo.annotations.Unique;
import javax.jdo.annotations.Version;
import javax.jdo.annotations.VersionStrategy;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.springframework.lang.Nullable;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.DomainObjectLayout;
import org.apache.causeway.applib.annotation.Editing;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.applib.annotation.Optionality;
import org.apache.causeway.applib.annotation.PromptStyle;
import org.apache.causeway.applib.annotation.Property;
import org.apache.causeway.applib.annotation.PropertyLayout;
import org.apache.causeway.applib.annotation.Publishing;
import org.apache.causeway.applib.annotation.TableDecorator;
import org.apache.causeway.applib.annotation.Title;
import org.apache.causeway.applib.jaxb.PersistentEntityAdapter;
import org.apache.causeway.applib.layout.LayoutConstants;
import org.apache.causeway.applib.services.message.MessageService;
import org.apache.causeway.applib.services.repository.RepositoryService;
import org.apache.causeway.applib.services.title.TitleService;
import org.apache.causeway.applib.value.Blob;
import org.apache.causeway.extensions.fullcalendar.applib.CalendarEventable;
import org.apache.causeway.extensions.fullcalendar.applib.value.CalendarEvent;
import org.apache.causeway.extensions.pdfjs.applib.annotations.PdfJsViewer;

import static org.apache.causeway.applib.annotation.SemanticsOf.IDEMPOTENT;
import static org.apache.causeway.applib.annotation.SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.val;

import domainapp.modules.simple.SimpleModule;
import domainapp.modules.simple.dom.vehiculo.Vehiculo;
import domainapp.modules.simple.types.Documento;
import domainapp.modules.simple.types.Email;
import domainapp.modules.simple.types.Name;
import domainapp.modules.simple.types.Nombre;
import domainapp.modules.simple.types.Notes;
import domainapp.modules.simple.types.Telefono;

@PersistenceCapable(schema = SimpleModule.SCHEMA, identityType = IdentityType.DATASTORE)
@Unique(name = "Usuario__name__UNQ", members = { "name" })
@Queries({
		@Query(name = Usuario.NAMED_QUERY__FIND_BY_NAME_LIKE, value = "SELECT "
				+ "FROM domainapp.modules.simple.dom.usuario.Usuario " + "WHERE name.indexOf(:name) >= 0"),
		@Query(name = Usuario.NAMED_QUERY__FIND_BY_NAME_EXACT, value = "SELECT "
				+ "FROM domainapp.modules.simple.dom.usuario.Usuario " + "WHERE name == :name") })
@DatastoreIdentity(strategy = IdGeneratorStrategy.IDENTITY, column = "id")
@Version(strategy = VersionStrategy.DATE_TIME, column = "version")
@Named(SimpleModule.NAMESPACE + ".Usuario")
@DomainObject(entityChangePublishing = Publishing.ENABLED)
@DomainObjectLayout(tableDecorator = TableDecorator.DatatablesNet.class)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@XmlJavaTypeAdapter(PersistentEntityAdapter.class)
@ToString(onlyExplicitlyIncluded = true)
public class Usuario implements Comparable<Usuario>, CalendarEventable {

	static final String NAMED_QUERY__FIND_BY_NAME_LIKE = "SimpleObject.findByNameLike";
	static final String NAMED_QUERY__FIND_BY_NAME_EXACT = "SimpleObject.findByNameExact";

	public static Usuario withName(String name) {
	    return withName(name, null,null,null,null);
	}
	
	public static Usuario withName(final String name, String nombre, String documento, String telefono, String email) {
		val simpleObject = new Usuario();
		simpleObject.setName(name);
		simpleObject.setNombre(nombre);
		simpleObject.setDocumento(documento);
		simpleObject.setTelefono(telefono);
		simpleObject.setEmail(email);
		return simpleObject;
	}

	@Inject
	@NotPersistent
	RepositoryService repositoryService;
	@Inject
	@NotPersistent
	TitleService titleService;
	@Inject
	@NotPersistent
	MessageService messageService;

	@Title
	@Name
	@Getter
	@Setter
	@ToString.Include
	@PropertyLayout(fieldSetId = LayoutConstants.FieldSetId.IDENTITY, sequence = "1")
	private String name;
	@Nombre
	@Getter
	@Setter
	@ToString.Include
	@PropertyLayout(fieldSetId = LayoutConstants.FieldSetId.IDENTITY, sequence = "2")
	private String nombre;

	@Documento
	@PropertyLayout(fieldSetId = LayoutConstants.FieldSetId.IDENTITY, sequence = "3")
	@Getter
	@Setter
	private String documento;

	@Telefono
	@PropertyLayout(fieldSetId = "contactDetails", sequence = "1,5")
	@Getter
	@Setter
	private String telefono;

	@Email
	@PropertyLayout(fieldSetId = "contactDetails", sequence = "1,6")
	@Getter
	@Setter
	private String email;

	@Notes
	@Getter
	@Setter
	@Property(commandPublishing = Publishing.ENABLED, executionPublishing = Publishing.ENABLED)
	@PropertyLayout(fieldSetId = LayoutConstants.FieldSetId.DETAILS, sequence = "2")
	private String notes;

	@PropertyLayout(fieldSetId = LayoutConstants.FieldSetId.IDENTITY, sequence = "1")
	@Persistent(mappedBy = "usuario")
	private List<Vehiculo> vehiculos;

//	@PdfJsViewer
//	@Getter
//	@Setter
//	@Persistent(defaultFetchGroup = "false", columns = { @Column(name = "attachment_name"),
//			@Column(name = "attachment_mimetype"), @Column(name = "attachment_bytes") })
//	@Property()
//	@PropertyLayout(fieldSetId = "content", sequence = "1")
//	private Blob attachment;

	@Property(optionality = Optionality.OPTIONAL, editing = Editing.ENABLED)
	@PropertyLayout(fieldSetId = LayoutConstants.FieldSetId.DETAILS, sequence = "3")
	@Column(allowsNull = "true")
	@Getter
	@Setter
	private java.time.LocalDate lastCheckedIn;

	@Override
	public String getCalendarName() {
		return "Last checked-in";
	}

	@Override
	public CalendarEvent toCalendarEvent() {
		if (getLastCheckedIn() != null) {
			long epochMillis = getLastCheckedIn().toEpochSecond(LocalTime.MIDNIGHT,
					ZoneOffset.systemDefault().getRules().getOffset(getLastCheckedIn().atStartOfDay())) * 1000L;
			return new CalendarEvent(epochMillis, getCalendarName(), titleService.titleOf(this), getNotes());
		} else {
			return null;
		}
	}

	@Action(semantics = IDEMPOTENT, commandPublishing = Publishing.ENABLED, executionPublishing = Publishing.ENABLED)
	@ActionLayout(associateWith = "name", promptStyle = PromptStyle.INLINE, describedAs = "Updates the name of this object, certain characters ("
			+ PROHIBITED_CHARACTERS + ") are not allowed.")
	public Usuario updateName(@Name final String name, String nombre , String documento , String telefono , String email) {
		setName(name);
		setNombre(nombre);
	
		return this;
	}

	@MemberSupport
	public String default0UpdateName() {
		return getName();
	}

	@MemberSupport
	public String validate0UpdateName(final String newName) {
		for (char prohibitedCharacter : PROHIBITED_CHARACTERS.toCharArray()) {
			if (newName.contains("" + prohibitedCharacter)) {
				return "Character '" + prohibitedCharacter + "' is not allowed.";
			}
		}
		return null;
	}

	static final String PROHIBITED_CHARACTERS = "&%$!";

//	@Action(semantics = IDEMPOTENT, commandPublishing = Publishing.ENABLED, executionPublishing = Publishing.ENABLED)
//	@ActionLayout(associateWith = "attachment", position = ActionLayout.Position.PANEL)
//	public Usuario updateAttachment(@Nullable final Blob attachment) {
//		setAttachment(attachment);
//		return this;
//	}

//	@MemberSupport
//	public Blob default0UpdateAttachment() {
//		return getAttachment();
//	}

	@Action(semantics = NON_IDEMPOTENT_ARE_YOU_SURE)
	@ActionLayout(fieldSetId = LayoutConstants.FieldSetId.IDENTITY, position = ActionLayout.Position.PANEL, describedAs = "Deletes this object from the persistent datastore")
	public void delete() {
		final String title = titleService.titleOf(this);
		messageService.informUser(String.format("'%s' deleted", title));
		repositoryService.removeAndFlush(this);
	}

	private final static Comparator<Usuario> comparator = Comparator.comparing(Usuario::getName);

	@Override
	public int compareTo(final Usuario other) {
		return comparator.compare(this, other);
	}

}
