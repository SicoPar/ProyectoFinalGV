package domainapp.webapp.quartz.job;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import org.springframework.stereotype.Component;

import domainapp.modules.simple.dom.usuario.Usuario;
import domainapp.modules.simple.dom.usuario.Usuarios;

import org.apache.causeway.applib.services.iactnlayer.InteractionContext;
import org.apache.causeway.applib.services.iactnlayer.InteractionService;
import org.apache.causeway.applib.services.user.UserMemento;
import org.apache.causeway.applib.services.xactn.TransactionalProcessor;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Component
@RequiredArgsConstructor(onConstructor_ = {@Inject})
@Log4j2
public class SampleJob implements Job {

    private final InteractionService interactionService;
    private final TransactionalProcessor transactionalProcessor;
    private final Usuarios usuarios;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        final List<Usuario> all = all();
        log.info("{} objects in the database", all.size());
    }

    List<Usuario> all() {
        return call("sven", usuarios::listAll)
                .orElse(Collections.<Usuario>emptyList());
    }

    private <T> Optional<T> call(
            final String username,
            final Callable<T> callable) {

        return interactionService.call(
                InteractionContext.ofUserWithSystemDefaults(UserMemento.ofName(username)),
                () -> transactionalProcessor.callWithinCurrentTransactionElseCreateNew(callable))
                .ifFailureFail()
                .getValue();
    }
}
