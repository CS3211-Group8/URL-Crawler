# Concurrent Web Crawler

The project involves the use of concuurency to efficiently crawl URLs and storing the HTML files of the found URLs in a local directory. The data race issue of having HTMLs from duplicate URLs stored is also prevented using synchronization mechanisms and an efficient indexing system to check for duplicates.

A merger jar file will not be necessary in this case as the program is designed to populate the results findings as the program runs.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

The only requirement prior to executing the programs would be to set up the required jar file and the seed URL file to be taken as input.

1. Under the P1_ConcurrentURLCrawler/jar/ folder, extract the crawler.jar file.
2. Ensure that the seed url input file is named to seed.txt.
3. Place the compiled crawler.jar file along with the seed.txt file into an empty directory or local folder.

## Execution
The execution of the crawler.jar file will cause additional folder to be created in the selected directory.
The following command is executed to run the program for 24h , taking input from the seed.txt file and output results of crawled URLs to the res.txt file. The maximum number of HTMLs stored is indicated by the storedPageNum field. In this case, only 1000 HTML files will be stored in local directory.

```
java -jar crawler.jar -input seed.txt -output res.txt -time 24h -storedPageNum 1000
```

## Results
The list of crawled URLs will be indicated in the res.txt file while the HTMLs will be stored in the folder, html/.




