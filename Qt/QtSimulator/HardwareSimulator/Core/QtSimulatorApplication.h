/***
*@class QSimulatorApplication
*@construct QSimulatorApplication(int  argc, char *args[], char *evn[])
*@author  Obaro Isreal Johnson <johnson.obaro@hotmail.com>
*@constributors
*-> Obaro Isreal Johnson [2017]
*->
*@brief
* The module contains the class that handle the user interface base application
*/
#ifndef QSIMULATOR_APPLICATION_H
#define QSIMULATOR_APPLICATION_H
#include "QSimulatorApplicationInterface.h"

class QWidget;
class QApplication;

namespace System
{
	namespace Core
	{
		class QSimulatorApplication : QSimulatorApplicationInterface
		{
		private:
			QWidget      * widget_ptr;
			QApplication * qtApp_ptr;
			static QSimulatorApplicationInterface * instance_ptr;
		public:
			explicit QSimulatorApplication(int  argc, char *args[]);
			/***************************************************************************************************************
			 *	@brief
			 *		The member function set the main widget for the application.
			 *	@param {widget_ptr}
			 *		The widget object to show as the main ui for the application.
			 *	@return {void}
			 *
			 ****************************************************************************************************************/
			virtual void  setWidget(QWidget * const widget_ptr);

			
			/****************************************************************************************************************
			*	@brief
			*		The method is called to start the execution of the application.
			*	@return {int} the exit code of the application
			*****************************************************************************************************************/

			virtual int execute();
			/****************************************************************************************************************
			* @brief
			*  The method create an instance of the application base on the parameter by the command line.
			* @param   {argc} the number of argument passed.
			* @param   {args} the list of argument values 
			* @param   {evn} The evironment variables of the systems.
			* @return  {QSimulatorApplicationInterface * const} return the current instance if its exists else create a new one;
			*******************************************************************************************************************/
			static  QSimulatorApplicationInterface * const createInstance(int  argc, char *args[]);

			virtual  ~QSimulatorApplication();

		protected:
			/*
			*	@brief
			*		The function is called immediately when the view is  created.
			*   @param{widget} the main system widget
			*/
			virtual void onCreateWidget(QWidget *const widget);
		};
	}
}
#endif