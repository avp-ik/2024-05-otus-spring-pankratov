package ru.otus.hw.dao;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import lombok.RequiredArgsConstructor;

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

@RequiredArgsConstructor
public class CsvQuestionDao implements QuestionDao {
    private final TestFileNameProvider fileNameProvider;

    @Override
    public List<Question> findAll() {
        // Использовать CsvToBean
        // https://opencsv.sourceforge.net/#collection_based_bean_fields_one_to_many_mappings
        // Использовать QuestionReadException
        // Про ресурсы: https://mkyong.com/java/java-read-a-file-from-resources-folder/

        List<Question> questions = new ArrayList<>();

        try {
            // getQuestionDtoIterator() может вернуть null, но в этом случае выведится
            // сообщение File not found и перехватится исключение.
            Iterator<QuestionDto> questionDtoIterator = getQuestionDtoIterator();

            while (questionDtoIterator.hasNext()) {
                QuestionDto questionDto = questionDtoIterator.next();
                Question question = questionDto.toDomainObject();
                questions.add(question);
            }
        } catch (Exception e) {
            throw new QuestionReadException("Error to read file" + fileNameProvider.getTestFileName(), e);
        }

        return questions;
    }

    private Iterator<QuestionDto> getQuestionDtoIterator() {
        InputStream stream = getClass().getClassLoader().getResourceAsStream(fileNameProvider.getTestFileName());

        if (stream == null) {
            System.out.println("File " + fileNameProvider.getTestFileName() + " not found.");
            return null;
        }

        Reader reader = new InputStreamReader(stream);

        CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build();

        CsvToBean<QuestionDto> csvToBean = new CsvToBeanBuilder(reader)
                .withType(QuestionDto.class)
                .withSkipLines(1)
                .withIgnoreLeadingWhiteSpace(true)
                .withSeparator(';')
                .build();

        return csvToBean.iterator();
    }
}
