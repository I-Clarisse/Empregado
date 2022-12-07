import React from 'react';
import './App.css';
import {BrowserRouter as Router, Routes, Route} from "react-router-dom";
import Footer from './components/Footer'
import CreateEmployee from './components/createEmployee/CreateEmployee'
import ListEmployee from './components/listEmployees/ListEmployee';
import ViewEmployee from './components/ViewEmployee';
import Sidebar from './components/sidebar/Sidebar';
import Homepage from './components/homepage/homepage';

function App() {
  return (
    <div>
      <Router>
        <Sidebar/>
        <div className='container'>
          <Routes>
            <Route path='/' element={<Homepage/>}/>
            <Route path='/employees' element = {<ListEmployee/>}/>
            <Route path='/add-employee' element = {<CreateEmployee/>}/>
            <Route path='/view-employee/:id' element = {<ViewEmployee/>}/>
          </Routes>
        </div>
        <Footer/>
      </Router>
      </div>
  );
}

export default App;
