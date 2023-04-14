ECHO Start of Loop

FOR /L %%i IN (1, 1, 100) DO (
    ECHO %%i
    CALL "C:\Program Files\Intel\Power Gadget 3.6\PowerLog3.0.exe" -file note-app-jit\results-%%i.csv -cmd C:\Users\vicda\.jdks\graalvm-ce-19\bin\java.exe -XX:+UnlockExperimentalVMOptions -XX:+EnableJVMCI -XX:+UseJVMCICompiler -cp .\target\note-app-algorand-1.0-SNAPSHOT-jar-with-dependencies.jar mx.unam.cruz.victor.notes.NoteField
)