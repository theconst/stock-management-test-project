FROM flyway/flyway:10.11.0-alpine
LABEL authors="kko"

COPY src/main/resources/db/migration/* /flyway/sql/

ENTRYPOINT ["flyway"]
CMD ["-?"]