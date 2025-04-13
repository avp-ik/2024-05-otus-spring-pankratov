package ru.otus.hw.batch;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class JobConfig {

    public static final String MIGRATION_JOB_NAME = "Migration from H2 to MongoDB";

    @Autowired
    private final JobRepository jobRepository;

    @Bean
    public Job importJob(Step transformAuthorStep, Step transformGenreStep, Step transformBookStep,
                         Step transformCommentStep) {
        return new JobBuilder(MIGRATION_JOB_NAME, jobRepository)
                .incrementer(new RunIdIncrementer())
                .flow(transformAuthorStep)
                .next(transformGenreStep)
                .next(transformBookStep)
                .next(transformCommentStep)
                .end()
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(@NonNull JobExecution jobExecution) {
                        log.info("Start {}", MIGRATION_JOB_NAME);
                    }

                    @Override
                    public void afterJob(@NonNull JobExecution jobExecution) {
                        log.info("End {}", MIGRATION_JOB_NAME);
                    }
                })
                .build();
    }
}
