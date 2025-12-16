# Настройка IntelliJ IDEA для устранения предупреждений SQLite

Для устранения красных предупреждений при запуске программы в IntelliJ IDEA:

1. Откройте Run → Edit Configurations
2. Выберите конфигурацию запуска Main
3. В поле "VM options" добавьте:
   ```
   --enable-native-access=ALL-UNNAMED
   ```
4. Нажмите OK

Теперь предупреждения SQLite не будут отображаться.

