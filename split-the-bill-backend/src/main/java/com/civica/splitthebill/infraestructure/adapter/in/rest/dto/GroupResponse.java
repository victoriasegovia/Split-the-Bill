package com.civica.splitthebill.infraestructure.adapter.in.rest.dto;

/**
 * DTO returned by the REST API when a group is created or listed.
 * Exposes only the identifier and name of the group.
 */
public record GroupResponse(Long id, String name) {
}
