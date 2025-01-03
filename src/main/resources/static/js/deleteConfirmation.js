document.addEventListener('DOMContentLoaded', function () {
    const deleteButtons = document.querySelectorAll('.delete-button');

    deleteButtons.forEach(button => {
        button.addEventListener('click', function (event) {
            const projectTitle = button.getAttribute('data-project-title');
            const confirmDelete = confirm(`Are you sure you want to delete "${projectTitle}"?`);
            if (!confirmDelete) {
                event.preventDefault(); // Prevent form submission
            }
        });
    });
});
