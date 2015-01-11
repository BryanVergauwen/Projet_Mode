for /r %%a in (*.java) do (javac -cp "bin;ressources\bdd\sqlite-jdbc-3.8.7.jar" -d "bin" "%%a")
pause