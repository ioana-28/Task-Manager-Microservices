package org.server.taskservice.dto;

import java.util.Date;

public record TaskResponseDto(
		Long id,
		String title,
		String description,
		Date createdAt,
		Long ownerUserId
) {
}
