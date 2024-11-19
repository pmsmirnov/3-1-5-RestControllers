const adminPanel = document.getElementById("admin")
const userPanel = document.getElementById("user")
const addingPage = document.getElementById("adduser")

window.onload = () => {start()}

async function start() {

    adminPanel.style.display = 'none';
    userPanel.style.display = 'none';
    addingPage.style.display = 'none';

    await updateNav()

    let currentUser = await getAuthUser()
    if (currentUser.id === 0 && currentUser.nick === "anonymousUser") {
    } else {
        let rolesString = currentUser.rolesList
        if (rolesString.includes("ADMIN")) {
            await showAdminPanel()
        } else if (rolesString.toString().includes("USER")) {
            await showUserPanel()
            const adminPanelLinkFromUserDiv = document.getElementById('adminPanelLinkFromUserDiv')
            adminPanelLinkFromUserDiv.style.display = 'none'
            await updateTableAndNavOfUser()
        }
    }
}

async function getAuthUser() {
    return await fetch('/api/userself', {
        method: 'post',
        credentials: 'include',
        headers: {
            "Content-type": "application/json; charset=UTF-8"
        },
    }).then(response => response.json())
}

async function getUser(userId) {
    const response = await fetch('/api/user', {
        method: 'post',
        credentials: 'include',
        headers: {
            "Content-type": "application/json; charset=UTF-8"
        },
        body: JSON.stringify({
            "userId": userId,
        })
    })
    return await response.json();
}

async function getUsers() {
    const response = await fetch('/api/users', {
        method: 'post',
        credentials: 'include',
        headers: {
            "Content-type": "application/x-www-form-urlencoded; charset=UTF-8"
        },
    })
    return await response.json();
}

async function showAdminPanel() {
    await updateTableAndNav()
    userPanel.style.display = 'none';
    addingPage.style.display = 'none';
    adminPanel.style.display = 'block';

}

async function showUserPanel() {
    await updateTableAndNavOfUser()
    adminPanel.style.display = 'none';
    addingPage.style.display = 'none';
    userPanel.style.display = 'block';
}

async function updateTableAndNav() {
    let tBody = document.querySelector('#usersTbody');
    while (tBody.rows.length) {
        tBody.deleteRow(0);
    }
    getUsers().then(async us => {
        const users = await us
        users.forEach(user => {
            let row = tBody.insertRow()

            let cellId = row.insertCell()
            cellId.textContent = user.id
            cellId.classList.add('align-middle')
            cellId.id = user.id

            let cellNick = row.insertCell()
            cellNick.textContent = user.nick
            cellNick.classList.add('align-middle')
            cellNick.id = user.nick

            let cellFirstName = row.insertCell()
            cellFirstName.textContent = user.firstName
            cellFirstName.classList.add('align-middle')
            cellFirstName.id = user.firstName

            let cellLastName = row.insertCell()
            cellLastName.textContent = user.lastName
            cellLastName.classList.add('align-middle')
            cellLastName.id = user.lastName

            let celleMail = row.insertCell()
            celleMail.textContent = user.eMail
            celleMail.classList.add('align-middle')
            celleMail.id = user.eMail

            let cellRoles = row.insertCell()
            cellRoles.textContent = user.rolesList
            cellRoles.classList.add('align-middle')
            cellRoles.id = "roles"

            let cellEdit = row.insertCell()
            let buttonEdit = document.createElement('button');
            buttonEdit.setAttribute('class', "btn btn-info text-white")
            buttonEdit.setAttribute('type', "button")
            buttonEdit.setAttribute('data-id', user.id)
            buttonEdit.setAttribute("data-bs-toggle", "modal")
            buttonEdit.setAttribute('data-bs-target', "#editModalTemplate")
            buttonEdit.setAttribute('data-action', "edit")
            buttonEdit.textContent = "Edit"
            cellEdit.appendChild(buttonEdit)

            let cellDel = row.insertCell()
            let buttonDel = document.createElement('button');
            buttonDel.setAttribute('class', "btn btn-danger text-white")
            buttonDel.setAttribute('type', "button")
            buttonDel.setAttribute('data-id', user.id)
            buttonDel.setAttribute("data-bs-toggle", "modal")
            buttonDel.setAttribute('data-bs-target', "#deleteModalTemplate")
            buttonDel.setAttribute('data-action', "delete")
            buttonDel.textContent = "Delete"
            cellDel.appendChild(buttonDel)

        })
    })

    await updateNav()

}


async function updateTableAndNavOfUser() {
    let tBody = document.querySelector('#userTbody');
    while (tBody.rows.length) {
        tBody.deleteRow(0);
    }
    let user = await fetch('/api/userself', {
        method: 'post',
        credentials: 'include',
        headers: {
            "Content-type": "application/json; charset=UTF-8"
        },
    }).then(response => response.json())

    let row = tBody.insertRow()

    let cellId = row.insertCell()
    cellId.textContent = user.id
    cellId.classList.add('align-middle')
    cellId.id = user.id

    let cellNick = row.insertCell()
    cellNick.textContent = user.nick
    cellNick.classList.add('align-middle')
    cellNick.id = user.nick

    let cellFirstName = row.insertCell()
    cellFirstName.textContent = user.firstName
    cellFirstName.classList.add('align-middle')
    cellFirstName.id = user.firstName

    let cellLastName = row.insertCell()
    cellLastName.textContent = user.lastName
    cellLastName.classList.add('align-middle')
    cellLastName.id = user.lastName

    let celleMail = row.insertCell()
    celleMail.textContent = user.eMail
    celleMail.classList.add('align-middle')
    celleMail.id = user.eMail

    let cellRoles = row.insertCell()
    let rolesText = ""
    user.rolesList.forEach(role => rolesText = rolesText + " " + role)
    cellRoles.textContent = rolesText
    cellRoles.classList.add('align-middle')
    cellRoles.id = "roles"

    let spanName = document.querySelector('#name');
    let spanRoles = document.querySelector('#roles');
    spanName.textContent = user.nick
    spanRoles.textContent = rolesText

}

const editModal = document.getElementById('editModalTemplate');
if (editModal) {
    editModal.addEventListener('show.bs.modal', event => {
        const button = event.relatedTarget
        const id = button.getAttribute('data-id')
        const promiseUserDTO = getUser(id)
        editingEditModal(promiseUserDTO)
    })
}

const deleteModal = document.getElementById('deleteModalTemplate');
if (deleteModal) {
    deleteModal.addEventListener('show.bs.modal', event => {
        const button = event.relatedTarget
        const id = button.getAttribute('data-id')
        const promiseUserDTO = getUser(id)
        editingDeleteModal(promiseUserDTO)
    })
}

async function editingDeleteModal(promiseUser) {
    promiseUser.then(async u => {
        const user = await u

        const delModalInputId = deleteModal.querySelector('#userIdDel')
        delModalInputId.value = user.id

        const delModalInputNickname = deleteModal.querySelector('#nickNameDel')
        delModalInputNickname.value = user.nick

        const delModalInputFirstName = deleteModal.querySelector('#firstNameDel')
        delModalInputFirstName.value = user.firstName

        const delModalInputLastName = deleteModal.querySelector('#lastNameDel')
        delModalInputLastName.value = user.lastName

        const delModalInputeMail = deleteModal.querySelector('#eMailDel')
        delModalInputeMail.value = user.eMail

        const deleteButton = deleteModal.querySelector('#deleteButton')

        const delMSelectRoles = deleteModal.querySelector('#rolesDel')
        delMSelectRoles.options.length = 0
        user.rolesList.forEach(r => delMSelectRoles.add(new Option(r, r)))

        deleteButton.onclick = function () {

            const jsonDTO = {
                "id": user.id,
                "nick": user.nick,
                "firstName": user.firstName,
                "lastName": user.lastName,
                "eMail": user.eMail,
                "rolesList": user.rolesList,
                "password": "password"
            }
            fetch('/api/delete', {
                method: 'post',
                credentials: 'include',
                headers: {
                    "Content-type": "application/json; charset=UTF-8"
                },
                body: JSON.stringify(jsonDTO)
            }).then(bootstrap.Modal.getInstance(deleteModal).hide())
                .then(() => updateTableAndNav())
                .catch(function (error) {
                    console.log('Request failed', error);
                });

        }
    })
}

const editModalInputId = editModal.querySelector('#userId')
const editModalInputNickname = editModal.querySelector('#nickName')
const editModalInputFirstName = editModal.querySelector('#firstName')
const editModalInputLastName = editModal.querySelector('#lastName')
const editModalInputeMail = editModal.querySelector('#eMail')
const editMSelectRoles = editModal.querySelector('#rolesEdit')

async function editingEditModal(promiseUser) {
    promiseUser.then(async u => {
        const user = await u

        editModalInputId.value = user.id
        editModalInputNickname.value = user.nick
        editModalInputFirstName.value = user.firstName
        editModalInputLastName.value = user.lastName
        editModalInputeMail.value = user.eMail
    })
        .then(async () => {
            fetch('/api/roles', {
                method: 'post',
                credentials: 'include',
                headers: {
                    "Content-type": "application/x-www-form-urlencoded; charset=UTF-8"
                },
            })
                .then((response) => response.json())
                .then(function (data) {
                    const editMSelectRoles = editModal.querySelector('#rolesEdit')
                    editMSelectRoles.options.length = 0
                    for (let i = 0; i < data.length; i++) {
                        editMSelectRoles.innerHTML = editMSelectRoles.innerHTML +
                            '<option value="' + data[i]['trimName'] + '">' + data[i]['trimName'] + '</option>';
                    }

                })
                .catch(function (error) {
                    console.log('Request failed', error);
                });

            const editButton = editModal.querySelector('#editButton')
            editButton.onclick = function () {

                const s = editMSelectRoles.value
                if (s == undefined) {
                    editMSelectRoles.value = "USER"
                }
                const editMPassword = editModal.querySelector('#password')
                const p = editMPassword.value
                if (p == undefined) {
                    editMPassword.value = "password"
                }
                const jsonDTO = {
                    "id": editModalInputId.value,
                    "nick": editModalInputNickname.value,
                    "firstName": editModalInputFirstName.value,
                    "lastName": editModalInputLastName.value,
                    "eMail": editModalInputeMail.value,
                    "rolesList": [editMSelectRoles.value],
                    "password": editMPassword.value
                }
                fetch('/api/edit', {
                    method: 'post',
                    credentials: 'include',
                    headers: {
                        "Content-type": "application/json; charset=UTF-8"
                    },
                    body: JSON.stringify(jsonDTO)
                }).then(bootstrap.Modal.getInstance(editModal).hide())
                    .then(() => updateTableAndNav())
                    .catch(function (error) {
                        console.log('Request failed', error);
                    });

            }
        })
}

const selectRole = addForm.querySelector('#rolesSelect')

async function showAddUser() {
    userPanel.style.display = 'none';
    adminPanel.style.display = 'none';
    addingPage.style.display = 'block';
    await updateNav()
    fetch('/api/roles', {
        method: 'post',
        credentials: 'include',
        headers: {
            "Content-type": "application/x-www-form-urlencoded; charset=UTF-8"
        },
    })
        .then((response) => response.json())
        .then(function (data) {
            selectRole.options.length = 0
            for (let i = 0; i < data.length; i++) {
                selectRole.innerHTML = selectRole.innerHTML +
                    '<option value="' + data[i]['trimName'] + '">' + data[i]['trimName'] + '</option>';
            }

        })
        .catch(function (error) {
            console.log('Request failed', error);
        });

    const addForm = document.getElementById('addForm');
    const addButton = addForm.querySelector('#addButton')
    const inputNick = addForm.querySelector('#nick')
    const inputFirstName = addForm.querySelector('#first_name')
    const inputLastName = addForm.querySelector('#last_name')
    const inputMail = addForm.querySelector('#e_mail')
    const inputPassword = addForm.querySelector('#user_pswd')
    const selectRole = addForm.querySelector('#rolesSelect')


    addButton.onclick = function () {

        if (selectRole.value === null || selectRole.value === "") {
            selectRole.value = "USER"
        }
        const p = inputPassword.value
        if (p === undefined || p === "") {
            inputPassword.value = "password"
        }

        const jsonDTO = {
            "id": 0,
            "nick": inputNick.value,
            "firstName": inputFirstName.value,
            "lastName": inputLastName.value,
            "eMail": inputMail.value,
            "rolesList": [selectRole.value],
            "password": inputPassword.value
        }

        fetch('/api/adduser', {
            method: 'post',
            credentials: 'include',
            headers: {
                "Content-type": "application/json; charset=UTF-8"
            },
            body: JSON.stringify(jsonDTO)
        })
            .then(response => {
                if (response.ok) showAdminPanel();
            })
            .catch(function (error) {
                console.log('Request failed', error);
            });

    }

}

async function updateNav() {
    let activeUser = await getAuthUser();
    let spanName = document.querySelector('#name');
    let spanRoles = document.querySelector('#roles');
    let withRoles = document.querySelector('#withRoles');
    let navbarSupportedContent = document.querySelector('#navbarSupportedContentDiv');
    if (activeUser.id === 0 && activeUser.nick === "anonymousUser") {
        withRoles.style.display = 'none';
        navbarSupportedContent.style.display = 'block';
    } else {
        let rolesText = ""
        activeUser.rolesList.forEach(role => rolesText = rolesText + " " + role)
        spanName.textContent = activeUser.nick
        spanRoles.textContent = rolesText
        withRoles.style.display = 'block';
        navbarSupportedContent.style.display = 'none';
    }
}

