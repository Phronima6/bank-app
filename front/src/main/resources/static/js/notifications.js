function updateNotifications() {
    const notificationsElement = document.getElementById('notifications');
    fetch('http://front-service.bankapp.local/api/notifications')
        .then(response => response.json())
        .then(json => {
            let table = '<table style="width:100%;margin-left:auto;margin-right:auto;border-radius:2%;padding:10px;background-color:whitesmoke;">';
            table += '<tr><th colspan="3">Уведомления</th></tr>';
            json.forEach(message => {
                table += '<tr>';
                table += '<td>' + message + '</td>';
                table += '</tr>';
            });
            table += '</table>';
            notificationsElement.innerHTML = table;
        })
        .catch(error => {
            notificationsElement.innerHTML = 'Ошибка при получении уведомлений';
        });
}

setInterval(updateNotifications, 1000); 