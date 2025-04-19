package ru.otus.hw.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.Properties;

import static ru.otus.hw.batch.JobConfig.MIGRATION_JOB_NAME;

@RequiredArgsConstructor
@ShellComponent
public class BatchCommands {

    private final JobOperator jobOperator;

    private final JobExplorer jobExplorer;

    @SuppressWarnings("unused")
    @ShellMethod(value = "Start migration process", key = "smp")
    public void startMigrationProcess() throws Exception {
        Long executionId = jobOperator.start(MIGRATION_JOB_NAME, new Properties());
        System.out.println(jobOperator.getSummary(executionId));
    }

    @SuppressWarnings("unused")
    @ShellMethod(value = "Show info", key = "si")
    public void showInfo() {
        System.out.println(jobOperator.getJobNames());
        System.out.println(jobExplorer.getLastJobInstance(MIGRATION_JOB_NAME));
    }
}
