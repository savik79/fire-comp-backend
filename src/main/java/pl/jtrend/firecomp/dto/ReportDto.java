package pl.jtrend.firecomp.dto;

import lombok.Data;

@Data
public class ReportDto {
    private Long id;
    private String type; // WORD, XML, PDF
    private byte[] content;
    private Long eventId;
    private Long userId;
}
