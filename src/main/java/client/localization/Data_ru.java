package client.localization;

import java.util.ListResourceBundle;

public class Data_ru extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return contents;
    }

    private final Object[][] contents = new Object[][] {

            // AUTH SCENE
            {"Authorization page", "Страница авторизации"},
            {"Type here your login", "Введите здесь свой логин"},
            {"Type here your pass", "Введите здесь свой пропуск"},
            {"Sign in", "Вход"},
            {"Login failed", "Ошибка входа"},
            {"User already authorized (multi-session is not supported)", "Пользователь уже авторизован (мультисессионный режим не поддерживается)"},
            {"User successfully logged in", "Пользователь успешно вошел в систему"},
            {"Incorrect login or password", "Неверный логин или пароль"},
            {"Don't have an account yet?", "У вас еще нет учетной записи?"},
            {"Go to the registration page", "Перейдите на страницу регистрации"},


            // REG SCENE
            {"Registration page", "Страница регистрации"},
            {"New user login", "Новый логин пользователя"},
            {"New user pass", "Новый пропуск пользователя"},
            {"Sign up", "Зарегистрироваться"},
            {"User is already registered", "Пользователь уже зарегистрирован"},
            {"New user successfully registered", "Новый пользователь успешно зарегистрирован"},
            {"Already have an account?", "У вас уже есть учетная запись?"},
            {"Go to the authorization page", "Перейдите на страницу авторизации"},


            // AUTH-REG SCENE
            {"Login", "Логин"},
            {"Login must have at least 3 characters", "Логин должен содержать не менее 3 символов"},
            {"Login must have less than 20 characters", "Логин должен содержать менее 20 символов"},
            {"Login must contains of only alphabetic characters", "Логин должен состоять только из буквенных символов"},
            {"Password", "Пароль"},
            {"Password must have at least 3 characters", "Пароль должен содержать не менее 3 символов"},
            {"Password must have less than 20 characters", "Пароль должен содержать менее 20 символов"},


            // MAIN SCENE
            {"Console", "Консоль"},
            {"Table", "Таблица"},
            {"Plot", "График"},


            // USER TAB
            {"User", "Пользователь"},
            {"Icon", "Икона"},
            {"Username", "Имя пользователя"},
            {"Sign out", "Выход"},
            {"Are you sure to sign out? (all unsaved data will be deleted)", "Вы уверены, что хотите выйти из системы? (все несохраненные данные будут удалены)"},
            {"Delete user", "Удалить пользователя"},
            {"Are you sure to delete user? (all your elements will be deleted)", "Вы уверены, что хотите удалить пользователя? (все ваши элементы будут удалены)"},
            {"Are you really sure?", "Ты действительно уверен?"},
            {"Are you REALLY sure to DELETE USER? (ALL your elements WILL BE DELETED)", "Вы ДЕЙСТВИТЕЛЬНО уверены, что хотите УДАЛИТЬ ПОЛЬЗОВАТЕЛЯ? (ВСЕ ваши элементы БУДУТ УДАЛЕНЫ)"},
            {"Are you really really really sure?", "Ты действительно, действительно, действительно уверен?"},
            {"ARE YOU REALLY REALLY REALLY SURE TO DELETE USER? (ALL YOUR ELEMENTS WILL BE DELETED)", "ВЫ ДЕЙСТВИТЕЛЬНО ДЕЙСТВИТЕЛЬНО УВЕРЕНЫ, ЧТО НУЖНО УДАЛИТЬ ПОЛЬЗОВАТЕЛЯ? (ВСЕ ВАШИ ЭЛЕМЕНТЫ БУДУТ УДАЛЕНЫ)"},
            {"Settings", "Настройки"},
            {"Language", "Язык"},
            {"Theme", "Тема"},
            {"Default", "По умолчанию"},
            {"Dark", "Темный"},
            {"Are you sure to exit? (all unsaved data will be deleted)", "Вы уверены, что хотите выйти? (все несохраненные данные будут удалены)"},
            {"Exit", "Выход"},


            // CONSOLE TAB
            {"Save history to file", "Сохранить историю в файл"},
            {"Saved console history", "Сохраненная история консоли"},
            {"Choose the command you want to send", "Выберите команду, которую вы хотите отправить"},
            {"Send", "Отправить"},
            {"Choose command", "Выберите команду"},
            {"You should choose command to send!", "Вы должны выбрать команду для отправки!"},


            // ONE PARAM PANE
            {"Param filling page", "Страница заполнения параметров"},


            // MOVIE KEY PARAM PANE
            {"Element filling page", "Страница заполнения элементов"},


            // COMMAND
            {"Are you sure?", "Ты уверен?"},
            {"Are you sure to send command", "Вы уверены, что отправите команду"},
            {"with given argument?", "с данным аргументом?"},
            {"with given arguments?", "с приведенными аргументами?"},
            {"Enter length", "Введите длину"},
            {"Enter script filename", "Введите имя файла скрипта"},
            {"Enter mpaa rating", "Введите рейтинг mpaa"},
            {"Enter key to remove", "Введите клавишу для удаления"},
            {"Movie key to insert", "Клавиша для вставки фильма"},
            {"Movie key to replace", "Клавиша фильма для замены"},
            {"Movie key to update", "Ключ фильма для обновления"},
            {"Return", "Возвращать"},
            {"Confirm", "Подтверждать"},


            // CHECK INDEX ERRORS
            {"ERROR: Server is not responding, try later or choose another server :(", "ОШИБКА: Сервер не отвечает, попробуйте позже или выберите другой сервер :("},
            {"ERROR: User isn't logged in yet (or connection support time is out)", "ОШИБКА: Пользователь еще не вошел в систему (или время поддержки подключения истекло)"},
            {"ERROR: Your elements count limit exceeded", "ОШИБКА: Превышен лимит количества элементов"},
            {"ERROR: Movie with given key doesn't exist", "ОШИБКА: Фильм с заданным ключом не существует"},
            {"ERROR: You don't have permission to update movie with given key", "ОШИБКА: У вас нет разрешения на обновление фильма с помощью данного ключа"},
            {"ERROR: You have permission to update movie with given key", "ОШИБКА: У вас есть разрешение на обновление фильма с помощью данного ключа"},
            {"ERROR: Movie with given key already exists", "ОШИБКА: Фильм с заданным ключом уже существует"},
            {"ERROR: You can't replace movie with given key", "ОШИБКА: Вы не можете заменить фильм заданным ключом"},
            {"ERROR: You can't update movie with given key", "ОШИБКА: Вы не можете обновить фильм с помощью данного ключа"},
            {"OK", "ОК"},


            // ONE PARAM ERRORS
            {"ERROR: method \"setGUIArgs\" is not overridden", "ОШИБКА: метод \"setGUIArgs\" не переопределен"},
            {"ERROR: value must be integer", "ОШИБКА: значение должно быть целым числом"},
            {"ERROR: current script doesn't exists", "ОШИБКА: текущий сценарий не существует"},
            // ERROR: value must be one of: [G, PG, PG_13, R, NC_17]
            // ERROR: value must be integer


            // MOVIE FIELD ERRORS
            {"ERROR: value must be in range [-200,200]", "ОШИБКА: значение должно находиться в диапазоне [-200,200]"},
            {"ERROR: value must be float", "ОШИБКА: значение должно быть плавающим"},
            {"ERROR: field can't be null, String can't be empty", "ОШИБКА: поле не может быть нулевым, строка не может быть пустой"},
            {"ERROR: name must have less than 20 characters", "ОШИБКА: имя должно содержать менее 20 символов"},
            {"ERROR: value must be more than 0", "ОШИБКА: значение должно быть больше 0"},
            {"ERROR: value must be long", "ОШИБКА: значение должно быть длинным"},
            {"ERROR: value must be more than 60 and less than 300", "ОШИБКА: значение должно быть больше 60 и меньше 300"},
            {"ERROR: value must be integer", "ОШИБКА: значение должно быть целым числом"},
            {"ERROR: value must be one of: [G, PG, PG_13, R, NC_17]", "ОШИБКА: значение должно быть одним из: [G, PG, PG_13, R, NC_17]"},
            {"ERROR: name must have less than 20 characters", "ОШИБКА: имя должно содержать менее 20 символов"},
            {"ERROR: year must be at least 1900 (vampire-screenwriters is not supported)", "ОШИБКА: год должен быть не менее 1900 (вампиры-сценаристы не поддерживается)"},
            {"ERROR: field must have \"DD.MM.YYYY\" format", "ОШИБКА: поле должно иметь формат \"ДД.ММ.ГГГГ\""},


            // TABLE TAB
            {"Filter by", "Фильтровать по"},
            {"with value contains", "со значением, содержащим"},
            {"anything", "что-нибудь"},
            {"Do you think here is an old collection?", "Как вы думаете, здесь старая коллекция?"},
            {"Update", "Обновление"},
            {"or set", "или установить"},
            {"Auto-update", "Автоматическое обновление"},


            // PLOT TAB
            {"Selected movie", "Выбранный фильм"},
            {"nothing selected", "ничего не выбрано"},


            // TABS
            {"Edit", "Редактировать"},
            {"Remove", "Удалять"},


            // ERRORS
            {"Error", "Ошибка"},
            {"Something wrong", "Что-то не так"},
            {"Oops... Seems like evil goblins cut some wires... Try again later", "Ой... Похоже, злые гоблины перерезали какие-то провода... Повторите попытку позже"},
            {"FATAL", "Роковой"},
            {"FATAL ERROR: don't peek", "ФАТАЛЬНАЯ ОШИБКА: не подглядывайте"},
            {"not extends ElementCommand", "не расширяет ElementCommand"}
    };
}
