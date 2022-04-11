src     := {src,test,cert,../lib}
classes := bin

main    := Main
libs    := /usr/share/java/junit.jar:/usr/share/java/hamcrest-core.jar



all: mkdir compile
remake: clean all
makerun: all run

clean:
	@rm -rf $(classes)
mkdir:
	@mkdir -p $(classes)

compile:
	javac -cp $(libs):. -d $(classes) $(src)/*.java
run:
	@java -cp $(classes):$(libs):. $(main)

.PHONY: all remake makerun run

