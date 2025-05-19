# EcoMarket_Spa-Notificaciones
Este microservicio permite enviar correos electrónicos a los usuarios del sistema EcoMarket SPA Proyecto del proyecto semestral FullStack_1. Está diseñado para enviar notificaciones transaccionales como confirmaciones de compra, estado de pedidos u otros mensajes automáticos.

## Tecnologías y herramientas utilizadas

- Java 17
- Spring Boot 3.4.5
- Spring Web
- Spring Boot Starter Mail
- Mailtrap (entorno de pruebas de envío de correos)

## Configuración de Mailtrap

Agrega tus credenciales SMTP en `application.properties`:

```
spring.mail.host=smtp.mailtrap.io
spring.mail.port=2525
spring.mail.username=TU_USUARIO_MAILTRAP
spring.mail.password=TU_PASSWORD_MAILTRAP
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

Puedes obtener estos datos desde tu cuenta de [https://mailtrap.io](https://mailtrap.io).

## Endpoints disponibles

### `POST /api/notificaciones/enviar`

Envía un correo electrónico.

#### Ejemplo de Request (JSON)
```
{
  "destinatario": "usuario@ejemplo.com",
  "asunto": "Confirmación de compra",
  "cuerpo": "Gracias por tu compra en EcoMarket. Tu pedido está siendo procesado."
}
```

#### Respuesta
- `200 OK`: Correo enviado correctamente
- `500 Internal Server Error`: Fallo en el envío

## Pruebas

Este microservicio está diseñado para funcionar con Mailtrap en entorno de desarrollo. No requiere base de datos.

---
