Group members: Denise Danielle Tamesis (dtamesis), Jenny Wei (jwei34)
Subgroup members: Khosrow Arian (karian), Moon Hwan Kim (mkim104)

CODE DESCRIPTION

We have three classes, Node, Leaf, and Edge that all implement INode.
Each class represents a different component of the tree.
Node: A Node in a tree. Takes in a String (its attribute) and an Object
(defaultValue), which is the most common value among its edge values. Its Edges
are represented by a LinkedList.
Leaf: Decision Node at the end of a tree, returns an Object (the final decision).
Edge: Takes in an Object (the value of the previousNode’s attribute) and an
INode (nextNode) so that it has a reference to the next Node/Leaf in the tree.
Essentially, Edges connect one INode to the next.

ListObjsData implements IAttributeDataset, and represents a dataset in the form
of a table, with a field for the list of column attributes, and a field for the
list of data rows. The methods in this class that extract and manipulate the
data table are used to implement buildClassifier in the TreeGenerator class.

TreeGenerator creates a decision tree, given a dataset, and implements methods
to interact with the tree. buildClassifier uses all of the methods in the
ListObjsData class to manipulate the dataset, and create Node, Leaf, and Edge
objects accordingly to construct the tree. lookUpRecommendation finally uses
the lookUpDecision method of the INode on the root of the tree to get the
recommended decision.

Vegetable: implements IAttributeDatum. Its 4 attributes are based on the
example in the handout (String color, Boolean lowCarb, Boolean highFiber,
Boolean likeToEat). We used instances of Vegetables in our RecommenderTestSuite
to simplify our testing of various methods.

RecommenderTestSuite houses all of our tests for the methods in
IAttributeDataset, IAttributeDatum, and INode, where we use test vegetables for
simplicity.


-------------------------------------------------------------------------------

Q1: Choose one step of the hiring process that the article goes over
and discuss how bias manifests itself. Think about underlying reasons for this.

We found the section about shaping the candidate pool through advertisements
particularly interesting. We did not previously think about how similar
job-advertising algorithms are to regular product advertisement algorithms in
that they predict not who will be successful in the role, but rather, who is
most likely to click on that job ad. This makes sense when realizing that
companies have a limited budget and want to maximize the number of potential
candidates for their positions while minimizing their costs. Thus, especially if
the algorithm relies on existing employee data, it makes sense that it will
favor advertising to candidates who most closely fit a job’s stereotype, e.g.
female cashiers or black cab drivers. Though this might minimize companies’
costs, it certainly results in a skewed applicant pool and perpetuates existing
biases, ultimately reducing diversity and potentially causing companies to
miss out on great candidates who do not necessarily fit their job’s stereotype.

Q2. What do you notice in the differences between cis male and cis female hired
ratios printed out?

Unequal:
Cis Female ratio hired: 0.09, 0.21, 0.12, 0.04, 0.11
Cis Male ratio hired: 0.48, 0.42, 0.3, 0.43, 0.42

There definitely seems to be a consistent bias toward hiring males over females.
There generally seems to be a 0.3-0.4 difference in the hiring ratios.

Q3: Do you still see the bias in the different hiring ratio? If so, what
differences do you notice between the training datasets (also presented on the
Google sheet)? How do you think these differences affect the hiring bias of the
algorithm?

Equal:
Cis Female ratio hired: 0.1, 0.14, 0.12, 0.09, 0.07
Cis Male ratio hired: 0.15, 0.25, 0.19, 0.2, 0.12

Our results still show a small bias towards cis males, although the difference
between hiring ratios has definitely narrowed. It is interesting because the
main difference between our unequal and equal hiring predictions is the male
hiring ratio, which dropped from around 40% to be closer to 15%, whereas the
female hiring ratio remained about the same. Unlike in the spreadsheet, our
results showed hiring bias even when the algorithm was built from the equal
data. This could be because of the larger sample size of male candidates.
Nevertheless, it is clear that algorithms based on even slightly biased data
can yield biased predictions.

Q4: How does the approach your code used to choose which attribute to split on
impact the resulting bias, if at all?

Our code splits on attributes at random, which, in theory, should lower the
algorithmic bias; however, a bias seems to remain, because most common values
are based on the contents of the dataset. The smaller the sample size, the more
likely it is to experience more extreme variations upon sampling (if we imagine
partitioning as sampling). The datasets passed in have more data on male
candidates over female candidates, so the basis for comparison in the
algorithm is slightly more robust for male candidates, leading to a bias. In
short, the source of the bias is in the dataset itself because of the
difference in sample sizes.

Q5: If your hiring rates vary each time you build a new classifier, why does
this occur? If we fix the algorithm to choose the same attribute to split on
each time, could we eliminate the bias?

The variation in hiring rates occurs because of the way the data is split
differently in subsets each time, so the pool of candidates from which the code
builds subsequent nodes will vary each time. Fixing the algorithm to choose the
same attribute to split on each time will likely yield more consistent results,
but it does not mean it is less biased—it could even become more biased because
it might further exacerbate the bias towards candidates that consistently match
certain characteristics. Randomizing it allows us to compute a more well-rounded
average prediction that considers predictions from multiple different starting
points.

Q6: Do you still see evidence of hiring bias? Why or why not?

We do still see evidence of hiring bias because there seems to be correlation
between other attributes and gender. This essentially means that the returned
recommendations will continue to be the same for certain attribute value
combinations, such as less leadership experience being correlated with being
female. The decisions for candidates with less leadership, for example, would
likely be similar to those for candidates that are female. Therefore, this
yields similar results to the ratios we observed when gender was included in the
data.

Q7: What are some limitations of BiasTest (and other statistical measures of
bias), if any, as a metric for fairness in the hiring process or as a metric
for diversity and inclusion (in the workspace)? What are some strengths, if any?
Consider providing an example of fairness/unfairness or potential benefit/harm
that wouldn’t be captured by the results of BiasTest.

One strength of BiasTest is the randomization of attributes to split on when
making predictions as this does not produce predictions that are skewed towards
favoring certain attribute values, but rather the users can average the
predictions from multiple test methods. However, there are many limitations,
such as the small female sample size, which could skew the results based on an
unrepresentative pool of candidates. Additionally, correlations between
attributes associated with certain genders—such as females having less
leadership experience and shorter duration of last work experience—also limit
the generalizability of BiasTest predictions even when gender as an attribute
is removed. An example of potential harm is that BiasTest currently only looks
at cisgendered males and cisgendered females. Not including data on individuals
of other genders would simply make the bias against these marginalized groups
worse. As discussed in the article, in the example on funding research for
sickle cell and HIV treatments, “Should we be spending millions of dollars  to
develop data-intensive treatments that may or may not work down the line, or
should we divert that money towards bridging the gap between the haves and the
have-nots for the treatments that already exist?” If the data isn’t expanded
to more holistically include different identities, we cannot expect an AI
algorithm to reasonably make such important decisions.




