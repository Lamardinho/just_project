package com.example.just_project.config;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Fixes swagger view of pageable
 * <a href="https://stackoverflow.com/a/50851820">...</a>
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@ApiImplicitParams({
        @ApiImplicitParam(
                name = "page",
                dataType = "int",
                paramType = "query",
                value = "Results page you want to retrieve (0..N)",
                example = "0"
        ),
        @ApiImplicitParam(
                name = "size",
                dataType = "int",
                paramType = "query",
                value = "Number of records per page.",
                example = "10"
        ),
        @ApiImplicitParam(
                name = "sort",
                allowMultiple = true,
                dataType = "string",
                paramType = "query",
                value = "Sorting criteria in the format: property(,ASC|DESC). "
                        + "Default sort order is ascending. "
                        + "Multiple sort criteria are supported."
        )
})
public @interface ApiPageable {
}
