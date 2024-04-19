const table = document.getElementById('tableBody')
const spann = document.getElementById('span')

fetch("http://localhost:8080/api/user")
    .then((response) => {
        return response.json();
    })
    .then((user) => {
        let tr = `<tr><td>${user.id}</td>
                  <td>${user.username}</td>
                  <td>${user.name}</td>
                  <td>${user.age}</td>
                  <td>${user.email}</td>
                  <td>${user.roles.map(role => role.name.substring(5))}</td></tr>`
        table.innerHTML = tr

        let span = `<h5><b>${user.email}</b> with roles: ${user.roles.map(role => role.name.substring(5))}</h5>`
        spann.innerHTML = span

        let  roles = `${user.roles.map(role => role.name.toString())}`
        console.log(roles)
        if (roles.indexOf("ADMIN") === -1) {
            document.getElementById('admin').style.display = "none";
        }
    });


