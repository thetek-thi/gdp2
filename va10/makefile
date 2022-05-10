src      := src
lib      := ../libpkg/studiplayer/audio
cert     := cert/studiplayer/cert
classes  := bin

main     := studiplayer.ui.Player
test     := studiplayer.audio.Main
libs     := /usr/share/java/junit.jar:/usr/share/java/hamcrest/hamcrest.jar:/usr/lib/jvm/openjfx/javafx-swt.jar:/usr/lib/jvm/openjfx/javafx.base.jar:/usr/lib/jvm/openjfx/javafx.controls.jar:/usr/lib/jvm/openjfx/javafx.fxml.jar:/usr/lib/jvm/openjfx/javafx.graphics.jar:/usr/lib/jvm/openjfx/javafx.media.jar:/usr/lib/jvm/openjfx/javafx.swing.jar:/usr/lib/jvm/openjfx/javafx.web.jar:../jars/jl1.0.jar:../jars/jorbis-0.0.15.jar:../jars/studiplayer.jar:../jars/vorbisspi1.0.2.jar:../jars/jogg-0.0.7.jar:../jars/mp3spi1.9.5.jar:../jars/tritonus_share.jar
javafx   := /usr/lib/jvm/openjfx
playlist := playlists/playList.cert.m3u


all: mkdir compile
remake: clean all
makerun: all run
makerunpl: all runpl
maketest: all test

clean:
	@rm -rf $(classes)
mkdir:
	@mkdir -p $(classes)

compile:
	javac -cp $(libs):. -d $(classes) $(lib)/*.java $(src)/studiplayer/{audio,ui}/*.java $(cert)/*.java
run:
	@java -cp $(classes):$(libs):. --module-path $(javafx) --add-modules javafx.controls $(main)
runpl:
	@java -cp $(classes):$(libs):. --module-path $(javafx) --add-modules javafx.controls $(main) -- $(playlist)
test:
	@java -cp $(classes):$(libs):. --module-path $(javafx) --add-modules javafx.controls $(test)


.PHONY: all remake makerun run runpl test

