import itertools
import numpy as np
import matplotlib.pyplot as plt

class plot:

    def plot_confusion_matrix(self,matrix):
        class_names = ['Accept', 'Pend', 'Reject']
        confusion_matrix = plt.cm.Blues
        plt.imshow(matrix, interpolation='nearest', cmap=confusion_matrix)
        plt.title('Confusion matrix')
        plt.colorbar()
        tick_marks = np.arange(len(class_names))
        plt.xticks(tick_marks, class_names, rotation=45)
        plt.yticks(tick_marks, class_names)
        plot.plot_box_iteration(matrix, False)
        plt.tight_layout()
        plt.ylabel('Expected label')
        plt.xlabel('Predicted label')
        plt.savefig('Results.png')
        #plt.show()

    def plot_box_iteration(matrix, normalize=False):
        fmt = '.2f' if normalize else 'd'
        thresh = matrix.max() / 2.
        for i, j in itertools.product(range(matrix.shape[0]), range(matrix.shape[1])):
            plt.text(j, i, format(matrix[i, j], fmt),
                     horizontalalignment="center",
                     color="white" if matrix[i, j] > thresh else "black")