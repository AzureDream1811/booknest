package com.azure.booknest.book.dto.response;

import java.util.List;

public record GoogleBookVolumeInfo(
    String title,
    String subtitle,
    List<String> authors,
    String description,
    String publisher,
    String publishedDate,
    Integer pageCount,
    List<String> categories,
    GoogleBookImageLinks imageLinks,
    String language,
    List<GoogleBookIndustryIdentifier> industryIdentifiers
) {
    
}
