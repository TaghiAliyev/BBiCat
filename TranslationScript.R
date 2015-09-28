# Script that does the translation
# db is the database name from where the names can be fetched
# geneNames are the gene names in that specific domain
source("http://bioconductor.org/biocLite.R")
biocLite(db) # Should be generic
library(db, character.only = TRUE)
dbOb <- get(db);
result <- select(dbOb, geneNames, c("SYMBOL"))