document.addEventListener("DOMContentLoaded", function() {
    // Obtener el usuario autenticado desde Thymeleaf si está disponible
    var sessionId = document.getElementById("sessionUser") ? document.getElementById("sessionUser").textContent.trim() : null;

    if (!sessionId || sessionId === "anonymous") {
        // Si el usuario no está autenticado, generar un identificador único y almacenarlo en LocalStorage
        sessionId = localStorage.getItem("sessionId");
        if (!sessionId) {
            sessionId = crypto.randomUUID(); // Generar UUID único
            localStorage.setItem("sessionId", sessionId);
        }
    }

    console.log("Session ID:", sessionId);

    // Función para incluir `X-Session-ID` en cada petición
    window.fetchWithSession = function(url, options = {}) {
        if (!options.headers) {
            options.headers = {};
        }
        options.headers["X-Session-ID"] = sessionId;
        return fetch(url, options);
    };
});
