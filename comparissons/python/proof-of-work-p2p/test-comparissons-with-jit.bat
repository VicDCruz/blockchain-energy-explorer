ECHO Start of Loop

FOR /L %%i IN (1, 1, 100) DO (
    ECHO %%i
    CALL "C:\Program Files\Intel\Power Gadget 3.6\PowerLog3.0.exe" -file pow-jit\results-%%i.csv -cmd docker-compose up --scale pow-jit=4 pow-jit
)