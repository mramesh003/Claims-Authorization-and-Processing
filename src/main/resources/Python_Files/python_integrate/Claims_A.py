import pandas
import pickle
import itertools
import warnings
import numpy as np
import matplotlib.pyplot as plt
from sklearn import preprocessing
from sklearn import model_selection
from sklearn.metrics import classification_report
from sklearn.metrics import confusion_matrix
from sklearn.metrics import accuracy_score
from sklearn.tree import DecisionTreeClassifier
from sklearn.neighbors import KNeighborsClassifier
from sklearn.discriminant_analysis import LinearDiscriminantAnalysis
from sklearn.naive_bayes import GaussianNB
import mysql.connector
from io import BytesIO


class plot:

    def plot_confusion_matrix(matrix, classes,
                              normalize=False,
                              title='Confusion matrix',
                              confusionMatrix=plt.cm.Blues):

        plt.imshow(matrix, interpolation='nearest', cmap=confusionMatrix)
        plt.title(title)
        plt.colorbar()
        tick_marks = np.arange(len(classes))
        plt.xticks(tick_marks, classes, rotation=45)
        plt.yticks(tick_marks, classes)

        plot.plot_box_iteration(matrix, normalize=False)

        plt.tight_layout()
        plt.ylabel('True label')
        plt.xlabel('Predicted label')

        plt.show()

    def plot_box_iteration(matrix, normalize=False):

        fmt = '.2f' if normalize else 'd'
        thresh = matrix.max() / 2.
        for i, j in itertools.product(range(matrix.shape[0]), range(matrix.shape[1])):
            plt.text(j, i, format(matrix[i, j], fmt),
                     horizontalalignment="center",
                     color="white" if matrix[i, j] > thresh else "black")



class claim_adjudication:

    def form_dataset(self,csv_buffercontent, names):
        dataset = pandas.read_csv(csv_buffercontent, names=names, skiprows=1)

        print(dataset.shape)
        # class distribution
        print("==============================")
        print("Data set Group By")
        print(dataset.groupby('Claim Status').size())
        print("==============================")

        # Split-out validation dataset
        array = dataset.values
        X = array[:,0:25]
        Y = array[:,25]

        str_val = [2,4,6,8,11,12,13,18,19,20,21,23]

        warnings.simplefilter("ignore", UserWarning)

        for val in str_val:
            enc = preprocessing.LabelEncoder()
            enc.fit(X[:,val])
            X[:,val] = enc.transform(X[:,val])

        return X,Y;


    def multi_algo_result(self,X_train, Y_train):

        # Spot Check Algorithms
        models = []
        models.append(('Linear Discriminant', LinearDiscriminantAnalysis()))
        models.append(('KNeighbors', KNeighborsClassifier()))
        models.append(('Decision Tree', DecisionTreeClassifier()))
        models.append(('GaussianNB', GaussianNB()))

        # evaluate each model in turn
        results = []
        names = []
        for name, model in models:
            kfold = model_selection.KFold(n_splits=10, random_state=5)
            cv_results = model_selection.cross_val_score(model, X_train, Y_train, cv=kfold, scoring='accuracy')
            results.append(cv_results)
            names.append(name)
            msg = "%s: %f (%f)" % (name, cv_results.mean(), cv_results.std())
            print(msg)

        return results, names;

    def fit_and_train_Model(self,X_trainset, Y_trainset):
        # Make predictions on validation dataset
        dtc = DecisionTreeClassifier()
        dtc.fit(X_trainset, Y_trainset)
        return dtc;

 #Connection Function
    def dbconnection(self):
        cnx = mysql.connector.connect(user='user1', password='user1',
                                      host='10.220.103.153',
                                      database='claimsauthprocess')
        if cnx.is_connected():
            return cnx;

    def savemodel(self,dtc,csv_list):

        modelcontent = pickle.dumps(dtc)
        connection =self.dbconnection()
        cursor = connection.cursor()
        cursor.execute("insert into modelfiles(filename,filecontent,datacount,colcount,flag) values(%s,%s,%s,%s,%s)",(csv_list[0],modelcontent,csv_list[1],csv_list[2],"python"))
        connection.commit()

    def loadModel(self):
            connection = self.dbconnection()
            cursor = connection.cursor()
            query1 = ("SELECT filecontent from modelfiles where flag=%s order by id DESC limit 1")
            cursor.execute(query1,('python',))
            data = cursor.fetchone()
            model = pickle.loads(data[0])
            return model;

    def executeModel(self,result, X_validationset,  Y_validationset):

        predictions = result.predict(X_validationset)
        print("                           ")
        print("===========================")
        print("Model Prediction End Result")
        print("===========================")

        acc_score = accuracy_score(Y_validationset, predictions)
        print(acc_score)
        print("                           ")
        print("===========================")
        print("Model Confusion Matrix")
        print("===========================")

        cnf_matrix = confusion_matrix(Y_validationset, predictions)

        print(cnf_matrix)
        class_names = ['Accept', 'Pend', 'Reject']
        plot.plot_confusion_matrix(cnf_matrix, classes=class_names,
                              title='Confusion Matrix')
        print("===========================")
        print("Model Classification Report")
        print("===========================")
        print(classification_report(Y_validationset, predictions))
        return predictions;


    def generatePlot(self,results, names):
        # Compare Algorithms
        fig = plt.figure()
        fig.suptitle('Algorithm Comparison')
        ax = fig.add_subplot(111)
        plt.boxplot(results)
        ax.set_xticklabels(names)
        plt.show()
        return;

    def csvretrival(self,fileid):
     #connection established
        connection = self.dbconnection()
        cursor = connection.cursor()
        query1 = ("SELECT * from csvfiles where id = %s")
        cursor.execute(query1,(fileid,))
        data = cursor.fetchone()

        filename = str(data[1])
    # for saving the filename
        modelfile = filename.strip(".csv")
        modelname = modelfile + ".mdl"
    # row Count
        columncount = data[4]
    # column count
        datacount = data[5]
        csv_filecontent = str(data[2], 'ISO-8859-1')
        csv_list = [modelname,datacount,columncount]
        csv_bytecontent = bytes(csv_filecontent, 'utf-8')
    #Creating the buffer
        csv_buffercontent = BytesIO(csv_bytecontent)
     # header
     # dynamic header reading -------------------------------------------------------------
     # my_string = csv_filecontent.splitlines()[0]
     # names = my_string.split(',')

        names = ['Billing National Provider Identification Number',
             'Insured Policy Number for Subscriber',
             'Subscriber State',
             'Subscriber Postal Code',
             'Subscriber Birth Date',
             'Payer Identification',
             'Patient State',
             'Patient Zip Code',
             'Patient’s Birth Date',
             'Facility Type Code',
             'Claim Transaction Type',
             'Statement From and Statement Through Date',
             'Principal Diagnosis Code',
             'Admitting Diagnosis Code',
             'Attending Provider NPI',
             'Rendering Provider NPI',
             'Reffering provider NPI',
             'Revenue Code',
             'CPT Procedure Code',
             'Procedure Modifier 1',
             'Procedure Modifier 2',
             'Line Item Charge Amount',
             'Service Unit Count',
             'Service Date',
             'Service Facility Provider ID',
             'Claim Status']
     #Returning the file content as buffer and header
        return csv_buffercontent, names,csv_list;


    def train_model(self,fileid):
    #retriving the csv file from DB
        csv_buffercontent , names1,csv_list =self.csvretrival(fileid)
    #forming the Dataset
        X, Y = self.form_dataset(csv_buffercontent,names1)
    # Call fit_and_train_Model(X, Y) function# Fit and Save the model output
        dtc = self.fit_and_train_Model(X, Y)
    # Save the model file in DB
        self.savemodel(dtc,csv_list)
    #Returning the Model
        return 'success';

    def execute_model(self,testfileId):
        # Need to load the test CSV file from DB and form the Dataset
        csv_buffercontent, names1,csv_list = self.csvretrival(testfileId)
        X, Y = self.form_dataset(csv_buffercontent, names1)
        # Need to load the latest model file from DB
        result = self.loadModel()

        #splitting and generate plot ----------
        #X_train, X_validation, Y_train, Y_validation = model_selection.train_test_split(X, Y, test_size=validation_size,random_state=5)
        #results, names = self.multi_algo_result(X_train, Y_train)
        #self.generatePlot(results, names)

        # call executeModel(dtc, X_validationset,  Y_validationset) function
        predictions =self.executeModel(result, X, Y)

        #append logic ----------------
        # predictions = result.predict(X)
        # print(predictions)
        # result1 = []
        # index = 0
        # a = iter(X)
        # for i in a:
        #     j= np.append(i,predictions[index])
        #     result1.append(j)
        #     index = index + 1


        ## returning predictions - example['Accept','Pend','Accept'...]
        return predictions;
#Assigning size for the test data file (10%)
validation_size = 0.10





