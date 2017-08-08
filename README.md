OrmLite数据库的使用，包括增删改查，以及数据库版本升级

使用：

添加依赖

    compile 'com.j256.ormlite:ormlite-android:5.0'
    
创建model

  注意点：
  
      1：DatabaseTable定义数据库表，如果不指定，默认以类名为表名
      
      2：@DatabaseField(columnName ="",generatedId = true)
         DatabaseField定义一个数据库字段，id表示主键，columnName表示字段名称，generatedId表示自增id，不需要赋值
      
      3：如果指定generatedId，generatedId必须为Integer类型，如果是id则可以为String或其他，在dao文件中
         private Dao<People, Integer> peopleStringDao;//Integer根据学生id类型判断
         
      4：model中必须有一个空的构造函数
         
创建Dao文件

   注意点：
   
       1：创建一个类继承OrmLiteSqliteOpenHelper，重写onCreate，onUpgrade方法，增加构造方法
       
       2：需要指明数据库名称，数据库版本，并将参数填写至构造方法中
       
       3：createTable创建表，通过单例模式获取对象（参考翔哥的博客），获取Dao对象，并释放资源（参考张磊的项目）
       
       4：数据库升级一般是增加，删除，或者修改字段名称，当做了如上改动时，需要在model中做相应修改，然后在onUpgrade（）
          方法中，先调用dropTable删除，在调用oncreate创建，数据库版本号自增即可
          
       5：如果不想删除，可以在创建表的时候调用createTableIfNotExists（）方法，升级时则直接
       
              /**
               * 数据库版本更新
               *
               * @param database         数据库名
               * @param connectionSource
               * @param oldVersion       数据库版本
               * @param newVersion       数据库版本
               */
              @Override
              public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
          //        try {
          //            TableUtils.dropTable(connectionSource,People.class,true);
          //        } catch (SQLException e) {
          //            e.printStackTrace();
          //        }
                  onCreate(database,connectionSource);
          
              }即可
              
              版本号一定要自增
              
增删改查

  创建一个文件xxxDao，例如
  
         private Context context;
         private Dao<People, Integer> peopleStringDao;//Integer根据学生id类型判断
     
         public PeopleDao(Context context) {
             this.context = context;
             peopleStringDao = DatabaseHelper.getHelper(context).getPeopleDao(People.class);
         }
         
  添加增删改查方法
  
  
         /**
              * 增加
              *
              * @param people
              */
             public void add(People people) {
                 try {
                     peopleStringDao.create(people);
                 } catch (SQLException e) {
                     e.printStackTrace();
                 }
             }
         
             /**
              * 删除
              * @param id
              */
             public void delete(int id) {
                 try {
                     peopleStringDao.deleteById(id);
                 } catch (SQLException e) {
                     e.printStackTrace();
                 }
             }
         
         
             /**
              * 查询
              * @param id
              */
             public People query(int id){
                 try {
                     People people=peopleStringDao.queryForId(id);
                     return people;
                 } catch (SQLException e) {
                     e.printStackTrace();
                 }
         
                 return null;
             }
         
             /**
              * 查询所有
              * @return
              */
             public ArrayList<People> queryAll() throws SQLException {
                 return TransactionManager.callInTransaction(DatabaseHelper.getHelper(context).getConnectionSource(), new Callable<ArrayList<People>>() {
                     @Override
                     public ArrayList<People> call() throws Exception {
                         ArrayList<People> list= (ArrayList<People>) peopleStringDao.queryForAll();
                         return list;
                     }
                 });
             }
         
             /**
              * 修改
              * @param people
              */
             public void update(People people){
                 try {
                     peopleStringDao.update(people);
                 } catch (SQLException e) {
                     e.printStackTrace();
                 }
             }
             
             当然增删改查也不仅仅包含这么几个方法
             
![image](https://github.com/wangchang163/HistogramView/blob/master/images/ee.gif) 
  
     
         