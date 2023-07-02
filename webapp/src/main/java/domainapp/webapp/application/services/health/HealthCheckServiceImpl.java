package domainapp.webapp.application.services.health;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Service;

import domainapp.modules.simple.dom.usuario.Usuarios;

import org.apache.causeway.applib.services.health.Health;
import org.apache.causeway.applib.services.health.HealthCheckService;

@Service
@Named("domainapp.HealthCheckServiceImpl")
public class HealthCheckServiceImpl implements HealthCheckService {

    private final Usuarios usuarios;

    @Inject
    public HealthCheckServiceImpl(Usuarios usuarios) {
        this.usuarios = usuarios;
    }

    @Override
    public Health check() {
        try {
//            simpleObjects.ping();
            return Health.ok();
        } catch (Exception ex) {
            return Health.error(ex);
        }
    }
}
