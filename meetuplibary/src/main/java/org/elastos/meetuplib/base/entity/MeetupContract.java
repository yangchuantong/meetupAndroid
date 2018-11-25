package org.elastos.meetuplib.base.entity;

import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.*;
import org.web3j.abi.datatypes.generated.Bytes4;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import rx.Observable;
import rx.functions.Func1;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 * <p>
 * <p>Generated with web3j version 3.3.1.
 */
public class MeetupContract extends Contract {
    private static final String BINARY = "60806040523480156200001157600080fd5b506040516200191938038062001919833981016040908152815160208301519183015160608401516080850151928501949384019391929101908484620000817f01ffc9a7000000000000000000000000000000000000000000000000000000006401000000006200022b810204565b620000b57f80ac58cd000000000000000000000000000000000000000000000000000000006401000000006200022b810204565b620000e97f4f558e79000000000000000000000000000000000000000000000000000000006401000000006200022b810204565b8151620000fe90600590602085019062000298565b5080516200011490600690602084019062000298565b50620001497f780e9d63000000000000000000000000000000000000000000000000000000006401000000006200022b810204565b6200017d7f5b5e139f000000000000000000000000000000000000000000000000000000006401000000006200022b810204565b5050600c805433600160a060020a03199182168117909255600d805490911690911790558151620001b690601090602085019062000298565b508215620001c55782620001c9565b6000195b600f55600c8054600160a060020a031916600160a060020a038316179055620002207f1b2b8ef1000000000000000000000000000000000000000000000000000000006401000000006200022b8102620015641704565b50505050506200033d565b7fffffffff0000000000000000000000000000000000000000000000000000000080821614156200025b57600080fd5b7fffffffff00000000000000000000000000000000000000000000000000000000166000908152602081905260409020805460ff19166001179055565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10620002db57805160ff19168380011785556200030b565b828001600101855582156200030b579182015b828111156200030b578251825591602001919060010190620002ee565b50620003199291506200031d565b5090565b6200033a91905b8082111562000319576000815560010162000324565b90565b6115cc806200034d6000396000f30060806040526004361061015e5763ffffffff7c010000000000000000000000000000000000000000000000000000000060003504166301ffc9a7811461016357806306fdde0314610199578063081812fc14610223578063095ea7b31461025757806318160ddd1461027d57806319d1997a146102a457806319fa8f50146102b957806323b872dd146102eb5780632f745c59146103155780633cebb8231461033957806342842e0e1461035a57806342966c68146103845780634f558e791461039c5780634f6ccce7146103b45780636352211e146103cc57806370a08231146103e4578063715018a6146104055780638da5cb5b1461041a57806395d89b411461042f578063a22cb46514610444578063b88d4fde1461046a578063c87b56dd146104d9578063d0def521146104f1578063e985e9c51461054b578063eac989f814610572578063f2fde38b14610587578063f77c4791146105a8575b600080fd5b34801561016f57600080fd5b50610185600160e060020a0319600435166105bd565b604080519115158252519081900360200190f35b3480156101a557600080fd5b506101ae6105dc565b6040805160208082528351818301528351919283929083019185019080838360005b838110156101e85781810151838201526020016101d0565b50505050905090810190601f1680156102155780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b34801561022f57600080fd5b5061023b600435610673565b60408051600160a060020a039092168252519081900360200190f35b34801561026357600080fd5b5061027b600160a060020a036004351660243561068e565b005b34801561028957600080fd5b50610292610737565b60408051918252519081900360200190f35b3480156102b057600080fd5b5061029261073d565b3480156102c557600080fd5b506102ce610743565b60408051600160e060020a03199092168252519081900360200190f35b3480156102f757600080fd5b5061027b600160a060020a0360043581169060243516604435610767565b34801561032157600080fd5b50610292600160a060020a036004351660243561080a565b34801561034557600080fd5b5061027b600160a060020a0360043516610857565b34801561036657600080fd5b5061027b600160a060020a0360043581169060243516604435610890565b34801561039057600080fd5b5061027b6004356108b1565b3480156103a857600080fd5b506101856004356108be565b3480156103c057600080fd5b506102926004356108db565b3480156103d857600080fd5b5061023b600435610910565b3480156103f057600080fd5b50610292600160a060020a036004351661093a565b34801561041157600080fd5b5061027b61096d565b34801561042657600080fd5b5061023b6109ce565b34801561043b57600080fd5b506101ae6109dd565b34801561045057600080fd5b5061027b600160a060020a03600435166024351515610a3e565b34801561047657600080fd5b50604080516020601f60643560048181013592830184900484028501840190955281845261027b94600160a060020a038135811695602480359092169560443595369560849401918190840183828082843750949750610ac29650505050505050565b3480156104e557600080fd5b506101ae600435610aea565b60408051602060046024803582810135601f810185900485028601850190965285855261027b958335600160a060020a0316953695604494919390910191908190840183828082843750949750610b9f9650505050505050565b34801561055757600080fd5b50610185600160a060020a0360043581169060243516610bdb565b34801561057e57600080fd5b506101ae610c09565b34801561059357600080fd5b5061027b600160a060020a0360043516610c97565b3480156105b457600080fd5b5061023b610cb7565b600160e060020a03191660009081526020819052604090205460ff1690565b60058054604080516020601f60026000196101006001881615020190951694909404938401819004810282018101909252828152606093909290918301828280156106685780601f1061063d57610100808354040283529160200191610668565b820191906000526020600020905b81548152906001019060200180831161064b57829003601f168201915b505050505090505b90565b600090815260026020526040902054600160a060020a031690565b600061069982610910565b9050600160a060020a0383811690821614156106b457600080fd5b33600160a060020a03821614806106d057506106d08133610bdb565b15156106db57600080fd5b6000828152600260205260408082208054600160a060020a031916600160a060020a0387811691821790925591518593918516917f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b92591a4505050565b60095490565b600f5481565b7f01ffc9a70000000000000000000000000000000000000000000000000000000081565b6107713382610cc6565b151561077c57600080fd5b600160a060020a038316151561079157600080fd5b600160a060020a03821615156107a657600080fd5b6107b08382610d25565b6107ba8382610d87565b6107c48282610e8e565b8082600160a060020a031684600160a060020a03167fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef60405160405180910390a4505050565b60006108158361093a565b821061082057600080fd5b600160a060020a038316600090815260076020526040902080548390811061084457fe5b9060005260206000200154905092915050565b600c54600160a060020a0316331461086e57600080fd5b600d8054600160a060020a031916600160a060020a0392909216919091179055565b6108ac8383836020604051908101604052806000815250610ac2565b505050565b6108bb3382610ed7565b50565b600090815260016020526040902054600160a060020a0316151590565b60006108e5610737565b82106108f057600080fd5b60098054839081106108fe57fe5b90600052602060002001549050919050565b600081815260016020526040812054600160a060020a031680151561093457600080fd5b92915050565b6000600160a060020a038216151561095157600080fd5b50600160a060020a031660009081526003602052604090205490565b600c54600160a060020a0316331461098457600080fd5b600c54604051600160a060020a03909116907ff8df31144d9c2f0f6b59d69b8b98abd5459d07f2742c4df920b25aae33c6482090600090a2600c8054600160a060020a0319169055565b600c54600160a060020a031681565b60068054604080516020601f60026000196101006001881615020190951694909404938401819004810282018101909252828152606093909290918301828280156106685780601f1061063d57610100808354040283529160200191610668565b600160a060020a038216331415610a5457600080fd5b336000818152600460209081526040808320600160a060020a03871680855290835292819020805460ff1916861515908117909155815190815290519293927f17307eab39ab6107e8899845ad3d59bd9653f200f220920489ca2b5937696c31929181900390910190a35050565b610acd848484610767565b610ad984848484610fd1565b1515610ae457600080fd5b50505050565b6060610af5826108be565b1515610b0057600080fd5b6000828152600b602090815260409182902080548351601f600260001961010060018616150201909316929092049182018490048402810184019094528084529091830182828015610b935780601f10610b6857610100808354040283529160200191610b93565b820191906000526020600020905b815481529060010190602001808311610b7657829003601f168201915b50505050509050919050565b600d54600160a060020a0316331480610bc25750600c54600160a060020a031633145b1515610bcd57600080fd5b610bd7828261113e565b5050565b600160a060020a03918216600090815260046020908152604080832093909416825291909152205460ff1690565b6010805460408051602060026001851615610100026000190190941693909304601f81018490048402820184019092528181529291830182828015610c8f5780601f10610c6457610100808354040283529160200191610c8f565b820191906000526020600020905b815481529060010190602001808311610c7257829003601f168201915b505050505081565b600c54600160a060020a03163314610cae57600080fd5b6108bb81611193565b600d54600160a060020a031681565b600080610cd283610910565b905080600160a060020a031684600160a060020a03161480610d0d575083600160a060020a0316610d0284610673565b600160a060020a0316145b80610d1d5750610d1d8185610bdb565b949350505050565b81600160a060020a0316610d3882610910565b600160a060020a031614610d4b57600080fd5b600081815260026020526040902054600160a060020a031615610bd75760009081526002602052604090208054600160a060020a031916905550565b6000806000610d968585611204565b600084815260086020908152604080832054600160a060020a0389168452600790925290912054909350610dd190600163ffffffff61128d16565b600160a060020a038616600090815260076020526040902080549193509083908110610df957fe5b90600052602060002001549050806007600087600160a060020a0316600160a060020a0316815260200190815260200160002084815481101515610e3957fe5b6000918252602080832090910192909255600160a060020a0387168152600790915260409020805490610e70906000198301611464565b50600093845260086020526040808520859055908452909220555050565b6000610e9a838361129f565b50600160a060020a039091166000908152600760209081526040808320805460018101825590845282842081018590559383526008909152902055565b6000806000610ee68585611322565b6000848152600b60205260409020546002600019610100600184161502019091160415610f24576000848152600b60205260408120610f2491611488565b6000848152600a6020526040902054600954909350610f4a90600163ffffffff61128d16565b9150600982815481101515610f5b57fe5b9060005260206000200154905080600984815481101515610f7857fe5b60009182526020822001919091556009805484908110610f9457fe5b6000918252602090912001556009805490610fb3906000198301611464565b506000938452600a6020526040808520859055908452909220555050565b600080610fe685600160a060020a0316611372565b1515610ff55760019150611135565b6040517f150b7a020000000000000000000000000000000000000000000000000000000081523360048201818152600160a060020a03898116602485015260448401889052608060648501908152875160848601528751918a169463150b7a0294938c938b938b93909160a490910190602085019080838360005b83811015611088578181015183820152602001611070565b50505050905090810190601f1680156110b55780820380516001836020036101000a031916815260200191505b5095505050505050602060405180830381600087803b1580156110d757600080fd5b505af11580156110eb573d6000803e3d6000fd5b505050506040513d602081101561110157600080fd5b5051600160e060020a031981167f150b7a020000000000000000000000000000000000000000000000000000000014925090505b50949350505050565b600e5460009061115590600163ffffffff61137a16565b9050611160816108be565b15801561116f5750600f548111155b151561117a57600080fd5b600e8190556111898382611387565b6108ac81836113d6565b600160a060020a03811615156111a857600080fd5b600c54604051600160a060020a038084169216907f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e090600090a3600c8054600160a060020a031916600160a060020a0392909216919091179055565b81600160a060020a031661121782610910565b600160a060020a03161461122a57600080fd5b600160a060020a03821660009081526003602052604090205461125490600163ffffffff61128d16565b600160a060020a039092166000908152600360209081526040808320949094559181526001909152208054600160a060020a0319169055565b60008282111561129957fe5b50900390565b600081815260016020526040902054600160a060020a0316156112c157600080fd5b60008181526001602081815260408084208054600160a060020a031916600160a060020a03881690811790915584526003909152909120546113029161137a565b600160a060020a0390921660009081526003602052604090209190915550565b61132c8282610d25565b6113368282610d87565b6040518190600090600160a060020a038516907fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef908390a45050565b6000903b1190565b8181018281101561093457fe5b6113918282611409565b600980546000838152600a60205260408120829055600182018355919091527f6e1540171b6c0c960b71a7020d9f60077f6af931a8bbf590da0223dacf75c7af015550565b6113df826108be565b15156113ea57600080fd5b6000828152600b6020908152604090912082516108ac928401906114cc565b600160a060020a038216151561141e57600080fd5b6114288282610e8e565b6040518190600160a060020a038416906000907fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef908290a45050565b8154818355818111156108ac576000838152602090206108ac91810190830161154a565b50805460018160011615610100020316600290046000825580601f106114ae57506108bb565b601f0160209004906000526020600020908101906108bb919061154a565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061150d57805160ff191683800117855561153a565b8280016001018555821561153a579182015b8281111561153a57825182559160200191906001019061151f565b5061154692915061154a565b5090565b61067091905b808211156115465760008155600101611550565b600160e060020a0319808216141561157b57600080fd5b600160e060020a0319166000908152602081905260409020805460ff191660011790555600a165627a7a72305820d8f366b78f72fcd3750b6bbcbd259da361ff6bc65948cd40bb478532b10898e80029";

    protected MeetupContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected MeetupContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public List<OwnershipRenouncedEventResponse> getOwnershipRenouncedEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("OwnershipRenounced",
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }),
                Arrays.<TypeReference<?>>asList());
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(event, transactionReceipt);
        ArrayList<OwnershipRenouncedEventResponse> responses = new ArrayList<OwnershipRenouncedEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            OwnershipRenouncedEventResponse typedResponse = new OwnershipRenouncedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.previousOwner = (Address) eventValues.getIndexedValues().get(0);
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<OwnershipRenouncedEventResponse> ownershipRenouncedEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("OwnershipRenounced",
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }),
                Arrays.<TypeReference<?>>asList());
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, OwnershipRenouncedEventResponse>() {
            @Override
            public OwnershipRenouncedEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(event, log);
                OwnershipRenouncedEventResponse typedResponse = new OwnershipRenouncedEventResponse();
                typedResponse.log = log;
                typedResponse.previousOwner = (Address) eventValues.getIndexedValues().get(0);
                return typedResponse;
            }
        });
    }

    public List<OwnershipTransferredEventResponse> getOwnershipTransferredEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("OwnershipTransferred",
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }, new TypeReference<Address>() {
                }),
                Arrays.<TypeReference<?>>asList());
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(event, transactionReceipt);
        ArrayList<OwnershipTransferredEventResponse> responses = new ArrayList<OwnershipTransferredEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.previousOwner = (Address) eventValues.getIndexedValues().get(0);
            typedResponse.newOwner = (Address) eventValues.getIndexedValues().get(1);
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<OwnershipTransferredEventResponse> ownershipTransferredEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("OwnershipTransferred",
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }, new TypeReference<Address>() {
                }),
                Arrays.<TypeReference<?>>asList());
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, OwnershipTransferredEventResponse>() {
            @Override
            public OwnershipTransferredEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(event, log);
                OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
                typedResponse.log = log;
                typedResponse.previousOwner = (Address) eventValues.getIndexedValues().get(0);
                typedResponse.newOwner = (Address) eventValues.getIndexedValues().get(1);
                return typedResponse;
            }
        });
    }

    public List<TransferEventResponse> getTransferEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("Transfer",
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }, new TypeReference<Address>() {
                }, new TypeReference<Uint256>() {
                }),
                Arrays.<TypeReference<?>>asList());
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(event, transactionReceipt);
        ArrayList<TransferEventResponse> responses = new ArrayList<TransferEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            TransferEventResponse typedResponse = new TransferEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._from = (Address) eventValues.getIndexedValues().get(0);
            typedResponse._to = (Address) eventValues.getIndexedValues().get(1);
            typedResponse._tokenId = (Uint256) eventValues.getIndexedValues().get(2);
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<TransferEventResponse> transferEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("Transfer",
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }, new TypeReference<Address>() {
                }, new TypeReference<Uint256>() {
                }),
                Arrays.<TypeReference<?>>asList());
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, TransferEventResponse>() {
            @Override
            public TransferEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(event, log);
                TransferEventResponse typedResponse = new TransferEventResponse();
                typedResponse.log = log;
                typedResponse._from = (Address) eventValues.getIndexedValues().get(0);
                typedResponse._to = (Address) eventValues.getIndexedValues().get(1);
                typedResponse._tokenId = (Uint256) eventValues.getIndexedValues().get(2);
                return typedResponse;
            }
        });
    }

    public List<ApprovalEventResponse> getApprovalEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("Approval",
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }, new TypeReference<Address>() {
                }, new TypeReference<Uint256>() {
                }),
                Arrays.<TypeReference<?>>asList());
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(event, transactionReceipt);
        ArrayList<ApprovalEventResponse> responses = new ArrayList<ApprovalEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            ApprovalEventResponse typedResponse = new ApprovalEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._owner = (Address) eventValues.getIndexedValues().get(0);
            typedResponse._approved = (Address) eventValues.getIndexedValues().get(1);
            typedResponse._tokenId = (Uint256) eventValues.getIndexedValues().get(2);
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<ApprovalEventResponse> approvalEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("Approval",
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }, new TypeReference<Address>() {
                }, new TypeReference<Uint256>() {
                }),
                Arrays.<TypeReference<?>>asList());
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, ApprovalEventResponse>() {
            @Override
            public ApprovalEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(event, log);
                ApprovalEventResponse typedResponse = new ApprovalEventResponse();
                typedResponse.log = log;
                typedResponse._owner = (Address) eventValues.getIndexedValues().get(0);
                typedResponse._approved = (Address) eventValues.getIndexedValues().get(1);
                typedResponse._tokenId = (Uint256) eventValues.getIndexedValues().get(2);
                return typedResponse;
            }
        });
    }

    public List<ApprovalForAllEventResponse> getApprovalForAllEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("ApprovalForAll",
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }, new TypeReference<Address>() {
                }),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {
                }));
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(event, transactionReceipt);
        ArrayList<ApprovalForAllEventResponse> responses = new ArrayList<ApprovalForAllEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            ApprovalForAllEventResponse typedResponse = new ApprovalForAllEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._owner = (Address) eventValues.getIndexedValues().get(0);
            typedResponse._operator = (Address) eventValues.getIndexedValues().get(1);
            typedResponse._approved = (Bool) eventValues.getNonIndexedValues().get(0);
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<ApprovalForAllEventResponse> approvalForAllEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("ApprovalForAll",
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }, new TypeReference<Address>() {
                }),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {
                }));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, ApprovalForAllEventResponse>() {
            @Override
            public ApprovalForAllEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(event, log);
                ApprovalForAllEventResponse typedResponse = new ApprovalForAllEventResponse();
                typedResponse.log = log;
                typedResponse._owner = (Address) eventValues.getIndexedValues().get(0);
                typedResponse._operator = (Address) eventValues.getIndexedValues().get(1);
                typedResponse._approved = (Bool) eventValues.getNonIndexedValues().get(0);
                return typedResponse;
            }
        });
    }

    public RemoteCall<Bool> supportsInterface(Bytes4 _interfaceId) {
        final Function function = new Function("supportsInterface",
                Arrays.<Type>asList(_interfaceId),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {
                }));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<Utf8String> name() {
        final Function function = new Function("name",
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {
                }));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<Address> getApproved(Uint256 _tokenId) {
        final Function function = new Function("getApproved",
                Arrays.<Type>asList(_tokenId),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<TransactionReceipt> approve(Address _to, Uint256 _tokenId) {
        final Function function = new Function(
                "approve",
                Arrays.<Type>asList(_to, _tokenId),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Uint256> totalSupply() {
        final Function function = new Function("totalSupply",
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<Uint256> supplyLimit() {
        final Function function = new Function("supplyLimit",
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<Bytes4> InterfaceId_ERC165() {
        final Function function = new Function("InterfaceId_ERC165",
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes4>() {
                }));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<TransactionReceipt> transferFrom(Address _from, Address _to, Uint256 _tokenId) {
        final Function function = new Function(
                "transferFrom",
                Arrays.<Type>asList(_from, _to, _tokenId),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Uint256> tokenOfOwnerByIndex(Address _owner, Uint256 _index) {
        final Function function = new Function("tokenOfOwnerByIndex",
                Arrays.<Type>asList(_owner, _index),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<TransactionReceipt> changeController(Address _newController) {
        final Function function = new Function(
                "changeController",
                Arrays.<Type>asList(_newController),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> safeTransferFrom(Address _from, Address _to, Uint256 _tokenId) {
        final Function function = new Function(
                "safeTransferFrom",
                Arrays.<Type>asList(_from, _to, _tokenId),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> burn(Uint256 _tokenId) {
        final Function function = new Function(
                "BURN",
                Arrays.<Type>asList(_tokenId),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Bool> exists(Uint256 _tokenId) {
        final Function function = new Function("exists",
                Arrays.<Type>asList(_tokenId),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {
                }));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<Uint256> tokenByIndex(Uint256 _index) {
        final Function function = new Function("tokenByIndex",
                Arrays.<Type>asList(_index),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<Address> ownerOf(Uint256 _tokenId) {
        final Function function = new Function("ownerOf",
                Arrays.<Type>asList(_tokenId),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<Uint256> balanceOf(Address _owner) {
        final Function function = new Function("balanceOf",
                Arrays.<Type>asList(_owner),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<TransactionReceipt> renounceOwnership() {
        final Function function = new Function(
                "renounceOwnership",
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Address> owner() {
        final Function function = new Function("owner",
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<Utf8String> symbol() {
        final Function function = new Function("symbol",
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {
                }));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<TransactionReceipt> setApprovalForAll(Address _to, Bool _approved) {
        final Function function = new Function(
                "setApprovalForAll",
                Arrays.<Type>asList(_to, _approved),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> safeTransferFrom(Address _from, Address _to, Uint256 _tokenId, DynamicBytes _data) {
        final Function function = new Function(
                "safeTransferFrom",
                Arrays.<Type>asList(_from, _to, _tokenId, _data),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Utf8String> tokenURI(Uint256 _tokenId) {
        final Function function = new Function("tokenURI",
                Arrays.<Type>asList(_tokenId),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {
                }));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<TransactionReceipt> mint(Address _to, Utf8String _tokenURI, BigInteger weiValue) {
        final Function function = new Function(
                "mint",
                Arrays.<Type>asList(_to, _tokenURI),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteCall<Bool> isApprovedForAll(Address _owner, Address _operator) {
        final Function function = new Function("isApprovedForAll",
                Arrays.<Type>asList(_owner, _operator),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {
                }));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<Utf8String> data() {
        final Function function = new Function("uri",
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {
                }));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<TransactionReceipt> transferOwnership(Address _newOwner) {
        final Function function = new Function(
                "transferOwnership",
                Arrays.<Type>asList(_newOwner),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Address> controller() {
        final Function function = new Function("controller",
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }));
        return executeRemoteCallSingleValueReturn(function);
    }

    public static RemoteCall<MeetupContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, Utf8String _name, Utf8String _symbol, Uint256 _supplyLimit, Utf8String _classURI, Address _owner) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(_name, _symbol, _supplyLimit, _classURI, _owner));
        return deployRemoteCall(MeetupContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static RemoteCall<MeetupContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, Utf8String _name, Utf8String _symbol, Uint256 _supplyLimit, Utf8String _classURI, Address _owner) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(_name, _symbol, _supplyLimit, _classURI, _owner));
        return deployRemoteCall(MeetupContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static MeetupContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new MeetupContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static MeetupContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new MeetupContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static class OwnershipRenouncedEventResponse {
        public Log log;

        public Address previousOwner;
    }

    public static class OwnershipTransferredEventResponse {
        public Log log;

        public Address previousOwner;

        public Address newOwner;
    }

    public static class TransferEventResponse {
        public Log log;

        public Address _from;

        public Address _to;

        public Uint256 _tokenId;
    }

    public static class ApprovalEventResponse {
        public Log log;

        public Address _owner;

        public Address _approved;

        public Uint256 _tokenId;
    }

    public static class ApprovalForAllEventResponse {
        public Log log;

        public Address _owner;

        public Address _operator;

        public Bool _approved;
    }
}
