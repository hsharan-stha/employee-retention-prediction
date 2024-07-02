import React, {useState} from 'react';
import ExcelJS from 'exceljs';
import Sidebar from "./sidebar.tsx";
import {useMutation} from "@tanstack/react-query";
import {toast, ToastContainer} from "react-toastify";
import Axios from "axios";
import 'react-toastify/dist/ReactToastify.css';

function Check() {
    const [data, setData] = useState([]);
    const [isDragging, setIsDragging] = useState(false);

    const [holdfile, setHoldFile] = useState();
    const handleFileUpload = async (file) => {
        setHoldFile(file)
        const reader = new FileReader();

        reader.onload = async (e) => {
            const arrayBuffer = e.target.result;
            const workbook = new ExcelJS.Workbook();
            await workbook.xlsx.load(arrayBuffer);
            const worksheet = workbook.getWorksheet(1);
            const jsonData = [];

            worksheet.eachRow({includeEmpty: false}, (row, rowNumber) => {
                if (rowNumber === 1) {
                    return; // Skip header row
                }
                const rowData = {};
                row.eachCell({includeEmpty: false}, (cell, colNumber) => {
                    const header = worksheet.getRow(1).getCell(colNumber).text;
                    rowData[header] = cell.text;
                });
                jsonData.push(rowData);
            });

            setData(jsonData);
            // Add code to train your model with jsonData
        };

        reader.readAsArrayBuffer(file);
    };

    const handleDragOver = (e) => {
        e.preventDefault();
        setIsDragging(true);
    };

    const handleDragLeave = () => {
        setIsDragging(false);
    };

    const handleDrop = (e) => {
        e.preventDefault();
        setIsDragging(false);
        const file = e.dataTransfer.files[0];
        handleFileUpload(file);
    };

    const handleChange = (e) => {
        const file = e.target.files[0];
        handleFileUpload(file);
    };

    const notify = () => toast("Model trained and saved successfully!");

    const apiCallToProceed = useMutation({
        mutationKey: ["PROCEED_TRAINING"],
        mutationFn(file: any) {
            const formData = new FormData();
            formData.append("file", file);
            return Axios.post("http://localhost:8080/train-by-file", formData)
        }
    })

    const proceedTraining = () => {

        apiCallToProceed.mutate(holdfile, {
            onSuccess(res) {
                notify()
            }
        })

    }

    return (
        <div className="flex">
            <Sidebar active={'check'}/>
            <div className="w-3/4 p-5">

                <div className="mb-5">
                    <h3 className="text-xl font-bold mb-3">Train Data</h3>

                    <div
                        className={`border-4 border-dashed rounded-lg p-6 text-center ${isDragging ? 'border-blue-500' : 'border-gray-300'}`}
                        onDragOver={handleDragOver}
                        onDragLeave={handleDragLeave}
                        onDrop={handleDrop}
                    >
                        <input
                            type="file"
                            id="file-upload"
                            className="hidden"
                            onChange={handleChange}
                        />
                        <label
                            htmlFor="file-upload"
                            className="block text-xl text-gray-700 cursor-pointer"
                        >
                            Drag and drop your training data here or{' '}
                            <span className="text-blue-500 underline">browse</span>
                        </label>
                        <p className="mt-2 text-sm text-gray-500">Supported formats: .xlsx</p>
                    </div>
                </div>

                {data.length > 0 && (
                    <div>
                        <button onClick={proceedTraining}
                                className="border px-4 py-1 rounded-md bg-blue-700 text-white">Proceed Training
                        </button>
                        <div className="overflow-x-auto">
                            <table className="min-w-full bg-white border border-gray-300 rounded-lg">
                                <thead>
                                <tr>
                                    {Object.keys(data[0]).map((key) => (
                                        <th key={key}
                                            className="py-2 px-4 border-b border-gray-300 bg-gray-100 text-left text-sm font-medium text-gray-700">
                                            {key}
                                        </th>
                                    ))}
                                </tr>
                                </thead>
                                <tbody>
                                {data.map((row, index) => (
                                    <tr key={index} className="border-b border-gray-300 hover:bg-gray-50">
                                        {Object.values(row).map((value, idx) => (
                                            <td key={idx} className="py-2 px-4 text-sm text-gray-700">
                                                {value}
                                            </td>
                                        ))}
                                    </tr>
                                ))}
                                </tbody>
                            </table>
                        </div>
                    </div>
                )}
                <ToastContainer />
            </div>
        </div>
    );
}

export default Check;
