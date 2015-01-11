for /r %%a in (*.java) do (javac -cp "bin;ressources\bdd\sqliteJdbc.jar" -d "bin" "%%a")
pause