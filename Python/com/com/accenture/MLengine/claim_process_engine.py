import pandas
import pickle
import warnings
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
from io import BytesIO
import com.accenture.MLengine.database.dao as dao_layer
import com.accenture.MLengine.graph.matrix as Plot


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
        X_Dataset= array[:,0:25]
        Y_Dataset = array[:,25]

        str_val = [2,4,6,8,11,12,13,18,19,20,21,23]

        warnings.simplefilter("ignore", UserWarning)

        for val in str_val:
            enc = preprocessing.LabelEncoder()
            enc.fit(X_Dataset[:,val])
            X_Dataset[:,val] = enc.transform(X_Dataset[:,val])

        return X_Dataset,Y_Dataset;

    def multi_algo_result(self,X_train, Y_train):
        models = []
        models.append(('Linear Discriminant', LinearDiscriminantAnalysis()))
        models.append(('KNeighbors', KNeighborsClassifier()))
        models.append(('Decision Tree', DecisionTreeClassifier()))
        models.append(('GaussianNB', GaussianNB()))
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
        dtc = DecisionTreeClassifier()
        dtc.fit(X_trainset, Y_trainset)
        return dtc;

    def savemodel(self,dtc,csv_list):
        modelcontent = pickle.dumps(dtc)
        dao_obj.save_Model(csv_list,modelcontent)

    def loadModel(self):
            data = dao_obj.load_Model()
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
        matrix_obj.plot_confusion_matrix(cnf_matrix)
        print("===========================")
        print("Model Classification Report")
        print("===========================")
        print(classification_report(Y_validationset, predictions))
        return predictions,cnf_matrix;


    def generatePlot(self,results, names):
        fig = plt.figure()
        fig.suptitle('Algorithm Comparison')
        ax = fig.add_subplot(111)
        plt.boxplot(results)
        ax.set_xticklabels(names)
        plt.show()
        return;

    def csvretrival(self,fileid):
        data = dao_obj.csv_Retrival(fileid)
        filename = str(data[1])
        modelfile = filename.strip(".csv")
        modelname = modelfile + ".mdl"
        columncount = data[4]
        datacount = data[5]
        csv_filecontent = str(data[2], 'ISO-8859-1')
        csv_list = [modelname,datacount,columncount]
        csv_bytecontent = bytes(csv_filecontent, 'utf-8')
        csv_buffercontent = BytesIO(csv_bytecontent)

        names = ['Billing National Provider Identification Number',
             'Insured Policy Number for Subscriber',
             'Subscriber State',
             'Subscriber Postal Code',
             'Subscriber Birth Date',
             'Payer Identification',
             'Patient State',
             'Patient Zip Code',
             'Patientâ€™s Birth Date',
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
        return csv_buffercontent,names,csv_list;

    def Loadimage(self):
        with open("Results.png", "rb")as imageFile:
            image = imageFile.read()
            Image_Bytes = bytearray(image)
            return Image_Bytes;

    def train_model(self,fileid):
        csv_buffercontent,names1,csv_list =self.csvretrival(fileid)
        X, Y = self.form_dataset(csv_buffercontent,names1)
        dtc = self.fit_and_train_Model(X, Y)
        self.savemodel(dtc,csv_list)
        return 'success';

    def execute_model(self,testfileId):
        csv_buffercontent, names1,csv_list = self.csvretrival(testfileId)
        X, Y = self.form_dataset(csv_buffercontent, names1)
        result = self.loadModel()
        predictions,cnfmatrix = self.executeModel(result, X, Y)
        predictions_confmatrix = ','.join(str(e) for e in predictions) + "result" + ','.join(str(e) for e in cnfmatrix) +"result"
        return predictions_confmatrix;
#-----------------------------------------------------------------------------------
validation_size = 0.10

#Object creation
dao_obj= dao_layer.daoClass()
matrix_obj = Plot.plot()



