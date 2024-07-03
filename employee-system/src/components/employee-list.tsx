import React, {useContext, useState} from 'react'
import {Dialog, DialogBackdrop, DialogPanel, DialogTitle} from '@headlessui/react'
import {useForm} from "react-hook-form";
import {useMutation, useQuery} from "@tanstack/react-query";
import {toast, ToastContainer} from "react-toastify";
import Axios from "axios";
import 'react-toastify/dist/ReactToastify.css';
import {ExclamationTriangleIcon} from "@heroicons/react/16/solid";
import {Context} from "./dashboard.tsx";
import { PiBrain } from "react-icons/pi";

const EmployeeList: React.FC = () => {
    const {setReload}=useContext(Context)
    const [open, setOpen] = useState(false)
    const [pOpen, setPOpen] = useState(false)
    const [predictionText, setpredictionText] = useState("")
    const {register, handleSubmit} = useForm();

    const saveEmpApi = useMutation({
        mutationKey: ["SAVE_EMP"],
        mutationFn(data: any) {
            return Axios.post("http://localhost:8080/employee", data)
        }
    })

    const getAllData = useQuery({
        queryKey: ["GET_ALL_DATA"],
        queryFn() {
            return Axios.get("http://localhost:8080/employee")
        }
    })


    const notify = () => toast("Data saved sucessfully!");


    const submit = (data: any) => {
        setReload(false);
        saveEmpApi.mutate(data, {
            onSuccess(res) {
                notify()
                setOpen(false)
                getAllData.refetch()
                setReload(true);

            }
        })
    }

    const apiToPredict=useMutation({
        mutationKey:["PREDICT_BY_ID"],
        mutationFn(id:any){
            return Axios.get("http://localhost:8080/employee/predict/"+id)
        }
    })
    return (
        <div>
            <div className="flex flex-row justify-between">
                <h3 className="text-xl font-bold mb-3">Employee List</h3>
                <button onClick={() => setOpen(true)}
                        className="border px-4 py-1 rounded-md bg-blue-700 text-white "> Add Employee
                </button>
            </div>
            <div className="bg-white rounded-lg overflow-hidden border">
                <table className="min-w-full bg-white">
                    <thead>
                    <tr className={"bg-blue-100 border-b"}>
                        <th className="py-3 px-4 ">Name</th>
                        <th className="py-3 px-4 ">Age</th>
                        <th className="py-3 px-4 ">Department</th>
                        <th className="py-3 px-4 ">Job Title</th>
                        <th className="py-3 px-4  text-end">Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    {getAllData?.data?.data?.map(r => (
                        <tr key={r?.id} className={"border-b last:border-b-0"}>
                            <td className="py-3 px-4 ">{r?.fullName}</td>
                            <td className="py-3 px-4 ">{r?.age}</td>
                            <td className="py-3 px-4 ">{r?.department}</td>
                            <td className="py-3 px-4 ">{r?.jobTitle}</td>
                            <td className="py-3 px-4 flex justify-end">
                                <button onClick={()=>{
                                    apiToPredict.mutate(r?.id,{
                                        onSuccess(res){
                                            // toast(res?.data)
                                            setpredictionText(res?.data)
                                            setPOpen(true)
                                        }
                                    })
                                }} className="border flex items-center gap-2 px-4 py-1 rounded-md bg-green-700 text-white"><PiBrain />Predict
                                </button>
                            </td>
                        </tr>
                    ))}


                    </tbody>
                </table>
            </div>

            <Dialog className="w-full relative z-10" open={open} onClose={setOpen}>
                <DialogBackdrop
                    transition
                    className="fixed inset-0 bg-gray-500 bg-opacity-75 transition-opacity data-[closed]:opacity-0 data-[enter]:duration-300 data-[leave]:duration-200 data-[enter]:ease-out data-[leave]:ease-in"
                />

                <div className="fixed inset-0 z-10 w-full overflow-y-auto">
                    <div className="flex min-h-full items-end justify-center p-4 text-center sm:items-center sm:p-0">
                        <DialogPanel
                            transition
                            className="relative w-full transform overflow-hidden rounded-lg bg-white text-left shadow-xl transition-all data-[closed]:translate-y-4 data-[closed]:opacity-0 data-[enter]:duration-300 data-[leave]:duration-200 data-[enter]:ease-out data-[leave]:ease-in sm:my-8 sm:w-full sm:max-w-2xl data-[closed]:sm:translate-y-0 data-[closed]:sm:scale-95"
                        >
                            <form onSubmit={handleSubmit(submit)} className="w-full">

                                <div className="bg-white w-full px-4 pb-4 pt-5 sm:p-6 sm:pb-4">
                                    <div>
                                        <div className=" flex flex-col mt-3 text-center sm:mt-0 sm:text-left">
                                            <h3 className="text-xl font-bold mb-3">Add Employee</h3>

                                            <br/>
                                            <div className="mt-2 grid grid-cols-2 gap-4">
                                                <div>
                                                    <label className="block text-sm font-medium">Full Name</label>
                                                    <input
                                                        type="text"
                                                        {...register("fullName")}
                                                        className="mt-1 block w-full border border-gray-300 rounded-md p-2"
                                                        required
                                                    />
                                                </div>
                                                <div>
                                                    <label className="block text-sm font-medium">Age</label>
                                                    <input
                                                        type="number"
                                                        {...register("age")}
                                                        className="mt-1 block w-full border border-gray-300 rounded-md p-2"
                                                        required
                                                    />
                                                </div>

                                                <div>
                                                    <label className="block text-sm font-medium">Gender</label>
                                                    <select
                                                        {...register("gender")}
                                                        className="mt-1 block w-full border border-gray-300 rounded-md p-2"
                                                        required
                                                    >
                                                        <option value="">Select</option>
                                                        <option value="male">Male</option>
                                                        <option value="female">Female</option>
                                                        <option value="other">Other</option>
                                                    </select>
                                                </div>

                                                <div>
                                                    <label className="block text-sm font-medium">Address</label>
                                                    <input
                                                        type="text"
                                                        {...register("address")}
                                                        className="mt-1 block w-full border border-gray-300 rounded-md p-2"
                                                        required
                                                    />
                                                </div>

                                                <div>
                                                    <label className="block text-sm font-medium">Job Title</label>
                                                    <input
                                                        type="text"
                                                        {...register("jobTitle")}
                                                        className="mt-1 block w-full border border-gray-300 rounded-md p-2"
                                                        required
                                                    />
                                                </div>

                                                <div>
                                                    <label className="block text-sm font-medium">Department</label>
                                                    <select
                                                        {...register("department")}
                                                        className="mt-1 block w-full border border-gray-300 rounded-md p-2"
                                                        required
                                                    >
                                                        <option value="">Select</option>
                                                        <option value="IT">IT</option>
                                                        <option value="Operations">Operations</option>
                                                        <option value="HR">HR</option>
                                                        <option value="Sales">Sales</option>
                                                        <option value="Marketing">Marketing</option>
                                                    </select>
                                                </div>

                                                <div>
                                                    <label className="block text-sm font-medium">Length of
                                                        Service (years)</label>
                                                    <input
                                                        type="number"
                                                        {...register("lengthOfService")}

                                                        className="mt-1 block w-full border border-gray-300 rounded-md p-2"
                                                        required
                                                    />
                                                </div>

                                                <div>
                                                    <label className="block text-sm font-medium">Promotions
                                                        Received (in count)</label>
                                                    <input
                                                        type="number"
                                                        {...register("promotionsReceived")}
                                                        className="mt-1 block w-full border border-gray-300 rounded-md p-2"
                                                        required
                                                    />
                                                </div>

                                                <div>
                                                    <label className="block text-sm font-medium">Training
                                                        Opportunities</label>
                                                    <select
                                                        {...register("trainingOpportunities")}
                                                        className="mt-1 block w-full border border-gray-300 rounded-md p-2"
                                                        required
                                                    >
                                                        <option value="">Select</option>
                                                        <option value="yes">Yes</option>
                                                        <option value="no">No</option>
                                                    </select>
                                                </div>

                                                <div>
                                                    <label className="block text-sm font-medium">Working
                                                        Environment (0 - 10)</label>
                                                    <input
                                                        type="number" min={0} max={10}
                                                        {...register("workingEnvironment")}
                                                        className="mt-1 block w-full border border-gray-300 rounded-md p-2"
                                                        required
                                                    />
                                                </div>

                                                <div>
                                                    <label className="block text-sm font-medium">Management
                                                        Quality (0 - 10)</label>
                                                    <input
                                                        type="number" min={0} max={10}
                                                        {...register("managementQuality")}
                                                        className="mt-1 block w-full border border-gray-300 rounded-md p-2"
                                                        required
                                                    />
                                                </div>

                                                <div>
                                                    <label className="block text-sm font-medium">Job
                                                        Satisfaction (0 - 10)</label>
                                                    <input
                                                        type="number" min={0} max={10}
                                                        {...register("jobSatisfaction")}
                                                        className="mt-1 block w-full border border-gray-300 rounded-md p-2"
                                                        required
                                                    />
                                                </div>

                                                <div>
                                                    <label className="block text-sm font-medium">Personal Development
                                                        Opportunities (0 - 10)</label>
                                                    <input
                                                        type="number" min={0} max={10}
                                                        {...register("personalDevelopmentOpportunities")}
                                                        className="mt-1 block w-full border border-gray-300 rounded-md p-2"
                                                        required
                                                    />
                                                </div>

                                                {/*<div>*/}
                                                {/*    <label className="block text-sm font-medium">Left Reason</label>*/}
                                                {/*    <select*/}
                                                {/*        {...register("leftReason")}*/}
                                                {/*        className="mt-1 block w-full border border-gray-300 rounded-md p-2"*/}
                                                {/*        required*/}
                                                {/*    >*/}
                                                {/*        <option value="">Select</option>*/}
                                                {/*        <option value="personal">Personal</option>*/}
                                                {/*        <option value="job_related">Job Related</option>*/}
                                                {/*        <option value="management">Management</option>*/}
                                                {/*        <option value="other">Other</option>*/}
                                                {/*    </select>*/}
                                                {/*</div>*/}

                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div className="bg-gray-50 px-4 py-3 sm:flex sm:flex-row-reverse sm:px-6">
                                    <button
                                        type="submit"
                                        className="inline-flex w-full justify-center rounded-md bg-red-600 px-3 py-2 text-sm font-semibold text-white shadow-sm hover:bg-red-500 sm:ml-3 sm:w-auto"

                                    >
                                        Save
                                    </button>
                                    <button
                                        type="button"
                                        className="mt-3 inline-flex w-full justify-center rounded-md bg-white px-3 py-2 text-sm font-semibold text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 hover:bg-gray-50 sm:mt-0 sm:w-auto"
                                        onClick={() => setOpen(false)}
                                        data-autofocus
                                    >
                                        Cancel
                                    </button>
                                </div>
                            </form>
                        </DialogPanel>
                    </div>
                </div>
            </Dialog>
            <ToastContainer/>

            <Dialog className="relative z-10" open={pOpen} onClose={setPOpen}>
                <DialogBackdrop
                    transition
                    className="fixed inset-0 bg-gray-500 bg-opacity-75 transition-opacity data-[closed]:opacity-0 data-[enter]:duration-300 data-[leave]:duration-200 data-[enter]:ease-out data-[leave]:ease-in"
                />

                <div className="fixed inset-0 z-10 w-screen overflow-y-auto">
                    <div className="flex min-h-full items-end justify-center p-4 text-center sm:items-center sm:p-0">
                        <DialogPanel
                            transition
                            className="relative transform overflow-hidden rounded-lg bg-white text-left shadow-xl transition-all data-[closed]:translate-y-4 data-[closed]:opacity-0 data-[enter]:duration-300 data-[leave]:duration-200 data-[enter]:ease-out data-[leave]:ease-in sm:my-8 sm:w-full sm:max-w-lg data-[closed]:sm:translate-y-0 data-[closed]:sm:scale-95"
                        >
                            <div className="bg-white px-4 pb-4 pt-5 sm:p-6 sm:pb-4">
                                <div className="sm:flex sm:items-start">
                                    <div className="mx-auto flex h-12 w-12 flex-shrink-0 items-center justify-center rounded-full bg-red-100 sm:mx-0 sm:h-10 sm:w-10">
                                        <ExclamationTriangleIcon className="h-6 w-6 text-red-600" aria-hidden="true" />
                                    </div>
                                    <div className="mt-3 text-center sm:ml-4 sm:mt-0 sm:text-left">
                                        <DialogTitle as="h3" className="text-base font-semibold leading-6 text-gray-900">
                                            Employee Prediction Summary
                                        </DialogTitle>
                                        <div className="mt-2">
                                            <p className="text-sm text-gray-500">
                                                {predictionText}
                                            </p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div className="bg-gray-50 px-4 py-3 sm:flex sm:flex-row-reverse sm:px-6">

                                <button
                                    type="button"
                                    className="mt-3 inline-flex w-full justify-center rounded-md bg-white px-3 py-2 text-sm font-semibold text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 hover:bg-gray-50 sm:mt-0 sm:w-auto"
                                    onClick={() => setPOpen(false)}
                                    data-autofocus
                                >
                                    Close
                                </button>
                            </div>
                        </DialogPanel>
                    </div>
                </div>
            </Dialog>
        </div>
    );
};

export default EmployeeList;
