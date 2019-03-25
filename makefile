all:
	@echo "Compiling..."
	javac *.java

run:
	@echo "Running..."
	java Main

clean:
	rm -rf *.class
