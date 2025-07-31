FROM quay.io/keycloak/keycloak:26.1.3

ENV KC_BOOTSTRAP_ADMIN_USERNAME=admin
ENV KC_BOOTSTRAP_ADMIN_PASSWORD=admin
ENV KC_HEALTH_ENABLED=true
ENV KC_METRICS_ENABLED=true

COPY realm-config /opt/keycloak/data/import

CMD ["start-dev", "--import-realm"]

EXPOSE 8080