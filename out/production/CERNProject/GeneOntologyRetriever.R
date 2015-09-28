# Script for running the BioMart for getting the list of genes associated with gene
# ontology terms.

# Installing the necessary library
source("http://bioconductor.org/biocLite.R")
biocLite()
biocLite("biomaRt", ask = FALSE)
library("biomaRt")
# Configuring details
ensembl = useMart("ensembl")
ensembl = useDataset("hsapiens_gene_ensembl", mart = ensembl)


for (j in 1:length(whole_list)){
# geneNames is the name of array that we will receive

# Let's first check if the geneNames has been sent correctly

go_ids = getBM(attributes = "go_id", filters = "hgnc_symbol", values = whole_list[[j]], mart = ensembl)

# Now, for each ontology check how much of an actual intersection it is
# For this test, let's try 100%

counter = 0;
lengthOf = length(go_ids[,1]);
print(lengthOf)
if (lengthOf > 0){
for (i in 1:lengthOf)
{
	interestedGo = go_ids[i,1];
	if (is.na(interestedGo) && length(interestedGo) > 0){
	# We should get all related genes to this GO term
	geneList = getBM(attributes = "hgnc_symbol", filters = "go_id", values = interestedGo, mart = ensembl);
	intersectionOfTwo = intersect(whole_list[[1]], geneList[,1]);
	if (length(intersectionOfTwo) >= length(geneList) * 0.2)
	{
		counter = counter + 1;
	}
}
} 
}
#print(counter)
}