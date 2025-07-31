function updateExchangeRates() {
    const exchangeRatesElement = document.getElementById('exchange_rates');
    fetch('http://front-service.bankapp.local/api/rates')
        .then(response => response.json())
        .then(json => {
            let table = '<table style="width:100%;margin-left:auto;margin-right:auto;border-radius:2%;padding:10px;background-color:whitesmoke;">';
            table += '<tr><th colspan="3">Курсы валют по отношению к рублю</th></tr>';
            table += '<tr><th>Валюта</th><th>Обозначение</th><th>Курс</th></tr>';
            json.forEach(rate => {
                table += '<tr>';
                table += '<td>' + rate.title + '</td>';
                table += '<td>' + rate.name + '</td>';
                table += '<td>' + rate.value / 100 + '</td>';
                table += '</tr>';
            });
            table += '</table>';
            exchangeRatesElement.innerHTML = table;
        })
        .catch(error => {
            exchangeRatesElement.innerHTML = 'Ошибка при получении данных курсов валют';
        });
}

setInterval(updateExchangeRates, 1000); 