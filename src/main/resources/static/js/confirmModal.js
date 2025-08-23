document.addEventListener("DOMContentLoaded", () => {
    let formToSubmit = null;
    const modalMessage = document.getElementById("modalMessage");
    const confirmAction = document.getElementById("confirmAction");
    const confirmModal = new bootstrap.Modal(document.getElementById("confirmModal"));

    document.querySelectorAll(".confirm-button").forEach(button => {
        button.addEventListener("click", function () {
            const message = this.getAttribute("data-message");
            formToSubmit = this.closest("form");

            modalMessage.textContent = message;
            confirmModal.show();
        });
    });

    confirmAction.addEventListener("click", () => {
        if (formToSubmit) {
            formToSubmit.submit();
        }
    });
});
