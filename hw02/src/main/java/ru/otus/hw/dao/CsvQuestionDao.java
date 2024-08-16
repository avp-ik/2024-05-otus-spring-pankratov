package ru.otus.hw.dao;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.dao.dto.QuestionDto;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class CsvQuestionDao implements QuestionDao {
    private final TestFileNameProvider fileNameProvider;

    @Override
    public List<Question> findAll() {
        // Использовать CsvToBean
        // https://opencsv.sourceforge.net/#collection_based_bean_fields_one_to_many_mappings
        // Использовать QuestionReadException
        // Про ресурсы: https://mkyong.com/java/java-read-a-file-from-resources-folder/
        List<Question> questions = null;

        try (InputStream stream = Objects.requireNonNull(getClass()
                    .getClassLoader()
                    .getResourceAsStream(fileNameProvider.getTestFileName()), "File not defined.")) {
                if (stream == null) {
                    throw new Exception("File " + fileNameProvider.getTestFileName() + " not found.");
                }
                questions = getQuestionList(stream);

        } catch (Exception e) {
            throw new QuestionReadException("Error to read file" + fileNameProvider.getTestFileName(), e);
        }
        return questions;
    }

    private List<Question> getQuestionList(InputStream inputStream) throws Exception {
        List<Question> questions = new ArrayList<>();
        if (inputStream == null) {
            throw new Exception("Internal error: wrong inputStream");
        }
        Reader reader = new InputStreamReader(inputStream);
        try (CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build()) {
            if (csvReader == null) {
                throw new Exception("Internal error: couldn't initiate csvReader");
            }
            CsvToBean<QuestionDto> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(QuestionDto.class)
                    .withSkipLines(1)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withSeparator(';')
                    .build();
            if (csvToBean == null) {
                throw new Exception("Error of reading of file " + fileNameProvider.getTestFileName());
            }
            questions = convertIteratorToQuestionList(csvToBean.iterator());
            return questions;
        }
    }

    private List<Question> convertIteratorToQuestionList (Iterator<QuestionDto> questionDtoIterator) throws Exception {
        List<Question> questions = new ArrayList<>();
        if (questionDtoIterator == null) {
            throw new Exception("Internal error: wrong iterator");
        }

        while (questionDtoIterator.hasNext()) {
            QuestionDto questionDto = questionDtoIterator.next();
            Question question = questionDto.toDomainObject();
            questions.add(question);
        }
        return questions;
    }
}