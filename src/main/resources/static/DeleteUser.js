const formDeleteUser = document.getElementById('deleteForm')
const deleteModalClose = document.getElementById('deleteModalClose')


function deleteModal(id) {
    fetch("http://localhost:8080/api/admin/" + id).then(response => response.json())
        .then(deleteUser => {
            formDeleteUser.idDelete.value = deleteUser.id
            formDeleteUser.usernameDelete.value = deleteUser.username
            formDeleteUser.lastNameDelete.value = deleteUser.name
            formDeleteUser.ageDelete.value = deleteUser.age
            formDeleteUser.emailDelete.value = deleteUser.email
            formDeleteUser.passwordDelete.value = deleteUser.password

        })
}



formDeleteUser.addEventListener('submit', deleteUser => {
    deleteUser.preventDefault()
    let method = {
        method: 'DELETE'
    }

    fetch("http://localhost:8080/api/admin/"+ formDeleteUser.idDelete.value,method).then(() => {
        adminPage();
        deleteModalClose.click();
    })
})