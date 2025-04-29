.PHONY: all
all: opis.pdf

%.pdf: %.tex
	mkdir -p aux/
	pdflatex -output-directory=aux/ $^
	mv aux/$@ .
	rm -r aux/
