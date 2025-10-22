package pl.jtrend.firecomp.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.jtrend.firecomp.dto.ReportDto;
import pl.jtrend.firecomp.entity.Event;
import pl.jtrend.firecomp.entity.Report;
import pl.jtrend.firecomp.entity.User;
import pl.jtrend.firecomp.mapper.FireCompMapper;
import pl.jtrend.firecomp.repository.EventRepository;
import pl.jtrend.firecomp.repository.ReportRepository;
import pl.jtrend.firecomp.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class ReportService {

    private final ReportRepository reportRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final FireCompMapper fireCompMapper;

    // CREATE
    public ReportDto create(ReportDto dto) {
        Report report = fireCompMapper.toReport(dto);
        applyRelations(dto, report);
        Report saved = reportRepository.save(report);
        return fireCompMapper.toReportDto(saved);
    }

    // READ
    public ReportDto findById(Long id) {
        return reportRepository.findById(id)
                .map(fireCompMapper::toReportDto)
                .orElseThrow(() -> new EntityNotFoundException("Report not found with id " + id));
    }

    public Page<ReportDto> findAll(Pageable pageable) {
        return reportRepository.findAll(pageable).map(fireCompMapper::toReportDto);
    }

    // UPDATE
    public ReportDto update(Long id, ReportDto dto) {
        Report existing = reportRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Report not found with id " + id));

        // Update primitive fields
        existing.setContent(dto.getContent());
        // type is enum in entity; mapper handles conversion from String by re-mapping a temporary entity
        if (dto.getType() != null) {
            Report mapped = fireCompMapper.toReport(dto);
            existing.setType(mapped.getType());
        }

        // Update relations
        applyRelations(dto, existing);

        Report saved = reportRepository.save(existing);
        return fireCompMapper.toReportDto(saved);
    }

    // DELETE
    public void deleteById(Long id) {
        if (!reportRepository.existsById(id)) {
            throw new EntityNotFoundException("Report not found with id " + id);
        }
        reportRepository.deleteById(id);
    }

    private void applyRelations(ReportDto dto, Report report) {
        if (dto.getEventId() != null) {
            Event event = eventRepository.findById(dto.getEventId())
                    .orElseThrow(() -> new EntityNotFoundException("Event not found with id " + dto.getEventId()));
            report.setEvent(event);
        }
        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException("User not found with id " + dto.getUserId()));
            report.setUser(user);
        }
    }
}
