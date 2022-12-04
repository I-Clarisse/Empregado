import React from 'react';
import './App.css';
import {BrowserRouter as Router, Routes, Route} from "react-router-dom";
import Footer from './components/Footer'
import Header from './components/Header'
import CreateEmployee from './components/CreateEmployee'
import ListEmployee from './components/ListEmployee';
import ViewEmployee from './components/ViewEmployee';

function App() {
  return (
    <div>
      <Router>
        <Header/>
        <div className='container'>
          <Routes>
            <Route path='/' element = {<ListEmployee/>}/>
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
