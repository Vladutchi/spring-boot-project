document.addEventListener("DOMContentLoaded", () => {
    let formToSubmit = null;
    const modalMessage = document.getElementById("modalMessage");
    const confirmDelete = document.getElementById("confirmDelete");
    const deleteModal = new bootstrap.Modal(document.getElementById("deleteModal"));

    document.querySelectorAll(".delete-button").forEach(button => {
        button.addEventListener("click", function () {
            const projectTitle = this.getAttribute("data-project-title");
            formToSubmit = this.closest("form");

            modalMessage.textContent = `Are you sure you want to delete "${projectTitle}"?`;
            deleteModal.show();
        });
    });

    confirmDelete.addEventListener("click", () => {
        if (formToSubmit) {
            formToSubmit.submit();
        }
    });
});
