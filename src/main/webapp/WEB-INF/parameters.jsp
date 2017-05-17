<%--
  Created by IntelliJ IDEA.
  User: yerlan
  Date: 17/05/17
  Time: 9:05 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Parameters</title>
    <meta http-equiv="refresh" content="15" />
</head>
<body>
    <form action="parameters" method="get" id = "refffresh">
        <input type="submit" hidden>
        <p class="seconds"></p>
    </form>

    <form action="parameters" method="post">
        <p>Сгенерировано клиентов:<%= request.getAttribute("generatedClients")%>,
            Сгенерировано счетов:<%= request.getAttribute("generatedAccounts")%></p>
        <p>
            OUTPUT_SHEDULER_PERIOD
            <input type="number" name = "OUTPUT_SCHEDULER_PERIOD" value = "<%= request.getAttribute("OUTPUT_SCHEDULER_PERIOD")%>"
            title="Период запуска шедула по выводу сгенеренных данных в файлы в мс."/>
        </p>
        <p>
            TRANSACTIONS_PER_ITERATION
            <input type="number" name = "TRANSACTIONS_PER_ITERATION" value = "<%= request.getAttribute("TRANSACTIONS_PER_ITERATION")%>"
            title="Количество транзакций, генерируемых за одну итерацию"/>
        </p>
        <p>
            ACCOUNT_GENERATION_PERIOD
            <input type="number" name = "ACCOUNT_GENERATION_PERIOD" value = "<%= request.getAttribute("ACCOUNT_GENERATION_PERIOD")%>"
            title="Количество итераций между генерациями нового счета"/>
        </p>
        <p>
            CLIENT_GENERATION_PERIOD
            <input type="number" name = "CLIENT_GENERATION_PERIOD" value = "<%= request.getAttribute("CLIENT_GENERATION_PERIOD")%>"
            title="Количество итераций между генерациями нового клиента"/>
        </p>
        <p>
            GENERATE_DUPLICATES
            <input type="text" name = "GENERATE_DUPLICATES" value = "<%= request.getAttribute("GENERATE_DUPLICATES")%>"
            title="Генерировать дубликаты?"/>
        </p>
        <p>
            DUPLICATES_GENERATION_PERIOD
            <input type="number" name = "DUPLICATES_GENERATION_PERIOD" value = "<%= request.getAttribute("DUPLICATES_GENERATION_PERIOD")%>"
            title="Количество генераций между генерацией дубликата"/>
        </p>
        <p>
            SCHEDULER_PERIOD
            <input type="number" name = "SCHEDULER_PERIOD" value = "<%= request.getAttribute("SCHEDULER_PERIOD")%>"
            title="Время в милисекундах между итерациями для генераций сущностей"/>
        </p>
        <p>
            <input type="submit" value="Send"/>
        </p>
    </form>

    <script type="text/javascript">
        window.onload=function(){
            var counter = 30;
            var interval = setInterval(function() {
                counter--;
                $("#seconds").text(counter);
                if (counter == 0) {
                    redirect();
                    clearInterval(interval);
                }
            }, 1000);

        };

        function redirect() {
            document.getElementById("refffresh").submit();
        }
    </script>
</body>
</html>
