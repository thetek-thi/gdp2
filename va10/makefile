src     := src
lib     := ../libpkg/studiplayer/audio
cert    := cert/studiplayer/cert
classes := bin

main    := studiplayer.ui.Player
libs    := /usr/share/java/junit.jar:/usr/share/java/hamcrest/hamcrest.jar:/usr/lib/jvm/openjfx/javafx-swt.jar:/usr/lib/jvm/openjfx/javafx.base.jar:/usr/lib/jvm/openjfx/javafx.controls.jar:/usr/lib/jvm/openjfx/javafx.fxml.jar:/usr/lib/jvm/openjfx/javafx.graphics.jar:/usr/lib/jvm/openjfx/javafx.media.jar:/usr/lib/jvm/openjfx/javafx.swing.jar:/usr/lib/jvm/openjfx/javafx.web.jar:../jars/jl1.0.jar:../jars/jorbis-0.0.15.jar:../jars/studiplayer.jar:../jars/vorbisspi1.0.2.jar:../jars/jogg-0.0.7.jar:../jars/mp3spi1.9.5.jar:../jars/tritonus_share.jar
javafx  := /usr/lib/jvm/openjfx


all: mkdir compile
remake: clean all
makerun: all run

clean:
	@rm -rf $(classes)
mkdir:
	@mkdir -p $(classes)

compile:
	javac -cp $(libs):. -d $(classes) $(lib)/*.java $(src)/studiplayer/{audio,ui}/*.java #$(cert)/*.java
run:
	@java -cp $(classes):$(libs):. --module-path $(javafx) --add-modules javafx.controls $(main)

.PHONY: all remake makerun run

