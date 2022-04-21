src     := {src,test,cert,../lib}
classes := bin

main    := Main
libs    := /usr/share/java/junit.jar:/usr/share/java/hamcrest/hamcrest.jar:../jars/jl1.0.jar:../jars/jorbis-0.0.15.jar:../jars/studiplayer.jar:../jars/vorbisspi1.0.2.jar:../jars/jogg-0.0.7.jar:../jars/mp3spi1.9.5.jar:../jars/tritonus_share.jar:


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

