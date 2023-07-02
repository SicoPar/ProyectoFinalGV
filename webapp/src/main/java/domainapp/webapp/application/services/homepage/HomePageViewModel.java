package domainapp.webapp.application.services.homepage;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.causeway.applib.annotation.Collection;
import org.apache.causeway.applib.annotation.CollectionLayout;
import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.DomainObjectLayout;
import org.apache.causeway.applib.annotation.HomePage;
import org.apache.causeway.applib.annotation.Nature;
import org.apache.causeway.applib.annotation.ObjectSupport;
import org.apache.causeway.applib.annotation.TableDecorator;

import domainapp.modules.simple.SimpleModule;
import domainapp.modules.simple.dom.usuario.Usuario;
import domainapp.modules.simple.dom.usuario.Usuarios;

@Named(SimpleModule.NAMESPACE + ".HomePageViewModel")
@DomainObject(nature = Nature.VIEW_MODEL)
@HomePage
@DomainObjectLayout()
public class HomePageViewModel {

    @ObjectSupport public String title() {
        return getObjects().size() + " objects";
    }

    @Collection
    @CollectionLayout(tableDecorator = TableDecorator.DatatablesNet.class)
    public List<Usuario> getObjects() {
        return usuarios.listAll();
    }

    @Inject Usuarios usuarios;
}
