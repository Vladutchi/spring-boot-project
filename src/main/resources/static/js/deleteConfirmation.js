document.addEventListener('DOMContentLoaded', function () {
    const deleteButtons = document.querySelectorAll('.delete-button');
    const modal = document.getElementById('deleteModal');
    const modalMessage = document.getElementById('modalMessage');
    const confirmDelete = document.getElementById('confirmDelete');
    const cancelDelete = document.getElementById('cancelDelete');

    let deleteForm = null; // Track the form to delete

    deleteButtons.forEach(button => {
        button.addEventListener('click', function (event) {
            event.preventDefault(); // Prevent form submission
            const projectTitle = button.getAttribute('data-project-title');
            deleteForm = button.closest('form');
            modalMessage.textContent = "Are you sure you want to permanently delete this project and all its tasks?";
            modal.style.display = 'flex'; // Show the modal
        });
    });

    confirmDelete.addEventListener('click', function () {
        if (deleteForm) {
            deleteForm.submit(); // Submit the form
        }
        modal.style.display = 'none'; // Hide the modal
    });

    cancelDelete.addEventListener('click', function () {
        modal.style.display = 'none'; // Hide the modal
        deleteForm = null; // Clear form reference
    });
});
